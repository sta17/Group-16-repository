package com.example.botanistcompanionapp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import utilities.ExifUtil;
import recordPackage.Record;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Fragment_Photo extends Fragment {
	
	private int id;
	private Fragment_GPS gpsfrag;
	private final int PLANT = 0;
	private final int AREA = 1;
	private final int PLANT_CAM = 2;
	private final int PLANT_FILE = 3;
	private final int AREA_CAM = 4;
	private final int AREA_FILE = 5;
	private ImageButton area;
	private ImageButton plant;
	private Record rec = null;
	private String imageFileName = null;
	private String mCurrentPhotoPath = null;
	private Button Pback;
	private Button PNext;
	private View view;
	private String email;
	private String name;
	private String phone;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__photo, container, false);

		rec = new Record();
		Bundle args = getArguments();
		if(args != null){
		if(args.containsKey("RECORD")){
			rec = (Record) args.getSerializable("RECORD");
			name = args.getString("NAME");
			phone = args.getString("PHONE");
			email = args.getString("EMAIL");
			id = args.getInt("ID");
		}}
		
		initActivityScreenOrientPortrait();

 		PNext = (Button) view.findViewById(R.id.PNext);
		PNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle args = new Bundle();
				args.putSerializable("RECORD", rec);
				args.putString("NAME", name);
				args.putString("EMAIL", email);
				args.putString("PHONE", phone);
				args.putInt("ID", id);
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				gpsfrag = new Fragment_GPS();
				gpsfrag.setArguments(args);
				transaction.replace(id, gpsfrag);
				transaction.addToBackStack(null);
				transaction.commit();
			} });

		Pback = (Button) view.findViewById(R.id.PBack);
		Pback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getFragmentManager().popBackStack();
			}
		});

		area = (ImageButton) view.findViewById(R.id.areapicbutton);
		area.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				selectImage(AREA);
			}
		});

		plant = (ImageButton) view.findViewById(R.id.plantpicbutton);
		plant.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				selectImage(PLANT);
			}
		});
		
		updateImage(rec);
		
		return view;
	}

	public void updateImage(Record temp) {
		// update the photos here.
		rec = temp;
		if (rec != null) {
			Bitmap bm;
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

			if (rec.getSceneIMGPath() != null) {
				bm = BitmapFactory.decodeFile(rec.getSceneIMGPath(),
						btmapOptions);
				Bitmap orientedBitmap = ExifUtil.rotateBitmap(
						rec.getSceneIMGPath(), bm);
				area.setImageBitmap(orientedBitmap);
			}
			if (rec.getSpecimenIMGPath() != null) {
				bm = BitmapFactory.decodeFile(rec.getSpecimenIMGPath(),
						btmapOptions);
				Bitmap orientedBitmap = ExifUtil.rotateBitmap(
						rec.getSpecimenIMGPath(), bm);
				plant.setImageBitmap(orientedBitmap);
			}
		}
	}

	private void selectImage(final int imagecode) {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("Take Photo")) {

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");

					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

					if (imagecode == PLANT) {
						startActivityForResult(intent, PLANT_CAM);
					} else {
						startActivityForResult(intent, AREA_CAM);
					}

				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");

					if (imagecode == PLANT) {
						startActivityForResult(
								Intent.createChooser(intent, "Select File"),
								PLANT_FILE);
					} else {
						startActivityForResult(
								Intent.createChooser(intent, "Select File"),
								AREA_FILE);
					}

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PLANT_CAM || requestCode == AREA_CAM) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}

				try {
					File to = createImageFile();
					f.renameTo(to);
					galleryAddPic();

					Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					String tempPath = to.getAbsolutePath();
					bm = BitmapFactory.decodeFile(tempPath, btmapOptions);

					if (requestCode == AREA_CAM) {
						rec.setSceneIMGPath(tempPath);
						bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
						Bitmap orientedBitmap = ExifUtil.rotateBitmap(tempPath,
								bm);
						area.setImageBitmap(orientedBitmap);

					} else {
						rec.setSpecimenIMGPath(tempPath);
						bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
						Bitmap orientedBitmap = ExifUtil.rotateBitmap(tempPath,
								bm);
						plant.setImageBitmap(orientedBitmap);
					}

					f.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (requestCode == PLANT_FILE || requestCode == AREA_FILE) {
				Uri selectedImageUri = data.getData();

				String tempPath = getRealPathFromURI(selectedImageUri);

				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);

				if (requestCode == AREA_FILE) {
					rec.setSceneIMGPath(tempPath);
					bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
					Bitmap orientedBitmap = ExifUtil.rotateBitmap(tempPath, bm);
					area.setImageBitmap(orientedBitmap);
				} else {
					rec.setSpecimenIMGPath(tempPath);
					bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
					Bitmap orientedBitmap = ExifUtil.rotateBitmap(tempPath, bm);
					plant.setImageBitmap(orientedBitmap);
				}
			}
		}
	}

	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);
		if (cursor.getCount() != 1) {
			return -1;
		}
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	private String getRealPathFromURI(Uri contentURI) {
		String result;
		Cursor cursor = getActivity().getContentResolver().query(contentURI,
				null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file
								// path
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		getActivity().sendBroadcast(mediaScanIntent);
	}

	/**
	 * https://developer.android.com/training/camera/photobasics.html
	 * 
	 * @return
	 * @throws IOException
	 */
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		imageFileName = "JPEG_BOT_" + timeStamp + "_";
		// File storageDir =
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		File storageDir = new File(Environment.getExternalStorageDirectory()
				+ "/BotanistApp/Image/Plants");
		if (!storageDir.isDirectory() && !storageDir.exists()) {
			File Directory = new File(Environment.getExternalStorageDirectory()
					+ "/BotanistApp/Image/plants/");
			Directory.mkdirs();
		}

		String temp = imageFileName;
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);
		imageFileName = temp;

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "" + image.getAbsolutePath();
		return image;
	}

	/**
	 * http://stackoverflow.com/questions/3723823/i-want-my-android-application-
	 * to-be-only-run-in-portrait-mode
	 */
	private void initActivityScreenOrientPortrait() {
		// Avoid screen rotations (use the manifests android:screenOrientation
		// setting)
		// Set this to nosensor or potrait

		// Set window fullscreen
		getActivity().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		// Test if it is VISUAL in portrait mode by simply checking it's size
		boolean bIsVisualPortrait = (metrics.heightPixels >= metrics.widthPixels);

		if (!bIsVisualPortrait) {
			// Swap the orientation to match the VISUAL portrait mode
			if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		} else {
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}
	}

}