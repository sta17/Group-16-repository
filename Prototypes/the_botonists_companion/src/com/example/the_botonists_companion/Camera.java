package com.example.the_botonists_companion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import recordPackage.Record;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 
 * Contains Working Camera
 * 
 */
public class Camera extends Activity {
	private final int PLANT = 0;
	private final int AREA = 1;
	private final int PLANT_CAM = 2;
	private final int PLANT_FILE = 3;
	private final int AREA_CAM = 4;
	private final int AREA_FILE = 5;
	private ImageButton area;
	private ImageButton plant;
	private Record temprecord = null;
	private String imageFileName = null;
	private String mCurrentPhotoPath = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(0x7f030000);
		temprecord = new Record();
		area = (ImageButton) findViewById(R.id.areapicbutton);
		plant = (ImageButton) findViewById(R.id.plantpicbutton);

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("RECORD")) {
				temprecord = (Record) savedInstanceState
						.getSerializable("RECORD");
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

				if (temprecord.getSceneIMGPath() != null) {
					bm = BitmapFactory.decodeFile(temprecord.getSceneIMGPath(),
							btmapOptions);
					area.setImageBitmap(bm);
				}
				if (temprecord.getSpecimenIMGPath() != null) {
					bm = BitmapFactory.decodeFile(
							temprecord.getSpecimenIMGPath(), btmapOptions);
					plant.setImageBitmap(bm);
				}
			}}
			//} else {
		
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					boolean isNew = extras.getBoolean("RECORD", false);
					if (isNew) {
						temprecord = (Record) extras.getSerializable("RECORD");
						Bitmap bm;
						BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

						if (temprecord.getSceneIMGPath() != null) {
							bm = BitmapFactory.decodeFile(
									temprecord.getSceneIMGPath(), btmapOptions);
							area.setImageBitmap(bm);
						}
						if (temprecord.getSpecimenIMGPath() != null) {
							bm = BitmapFactory.decodeFile(
									temprecord.getSpecimenIMGPath(),
									btmapOptions);
							plant.setImageBitmap(bm);
						}
					}
				}
			//}
		//}

		Button next = (Button) findViewById(R.id.backcamera);
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), Camera.class);
				myIntent.putExtra("RECORD", temprecord);
				setResult(RESULT_OK, myIntent);
				finish();
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	public void clickFuncArea(View view) {
		selectImage(AREA);
	}

	public void clickFuncPlant(View view) {
		selectImage(PLANT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void selectImage(final int imagecode) {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(Camera.this);
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
						// intent.putExtra("RECORD", temprecord);
						startActivityForResult(
								Intent.createChooser(intent, "Select File"),
								PLANT_FILE);
					} else {
						// intent.putExtra("RECORD", temprecord);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
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
						area.setImageBitmap(bm);
						temprecord.setSceneIMGPath(tempPath);
					} else {
						plant.setImageBitmap(bm);
						temprecord.setSpecimenIMGPath(tempPath);
					}

					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream fOut = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						fOut = new FileOutputStream(file);
						bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (requestCode == PLANT_FILE || requestCode == AREA_FILE) {
				Uri selectedImageUri = data.getData();

				String tempPath = getPath(selectedImageUri, Camera.this);

				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);

				if (requestCode == AREA_FILE) {

					temprecord.setSceneIMGPath(tempPath);

					area.setImageBitmap(bm);

				} else {

					temprecord.setSpecimenIMGPath(tempPath);

					plant.setImageBitmap(bm);

				}

			}
		}
	}

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		// Cursor cursor = activity.managedQuery(uri, projection, null, null,
		// null);
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/*
	 * private File RenameToUniqueName(File oldimage){ try { // Create an image
	 * file name String timeStamp = new
	 * SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); imageFileName =
	 * "JPEG_BOT_" + timeStamp + "_";
	 * 
	 * File direct = new File(Environment.getExternalStorageDirectory() +
	 * "/BotanistApp");
	 * 
	 * if (!direct.exists()) { File Directory = new File("/BotanistApp/Image/");
	 * Directory.mkdirs(); }
	 * 
	 * File storageDir = new File(Environment.getExternalStorageDirectory() +
	 * "/BotanistApp");
	 * 
	 * File image;
	 * 
	 * image = File.createTempFile( imageFileName, // prefix ".jpg", // suffix
	 * storageDir // directory ); oldimage.renameTo(image); return oldimage; }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return oldimage; }
	 */

	/**
	 * 
	 */
	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	/**
	 * https://developer.android.com/training/camera/photobasics.html
	 * 
	 * @return
	 * @throws IOException
	 */
	private File createImageFile() throws IOException {

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		imageFileName = "JPEG_BOT_" + timeStamp + "_";

		// File storageDir =
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		File storageDir = new File(Environment.getExternalStorageDirectory()
				+ "/BotanistApp/Image/Plants");

		if (!storageDir.isDirectory() && !storageDir.exists()) {
			File Directory = new File(Environment.getExternalStorageDirectory() + "/BotanistApp/Image/plants/");
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

	@Override
	protected void onSaveInstanceState(Bundle tosend) {
		tosend.putSerializable("RECORD", temprecord);
		super.onSaveInstanceState(tosend);
	}

	@Override
	public void onBackPressed() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("RECORD", temprecord);

		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		setResult(RESULT_OK, mIntent);
		super.onBackPressed();
	}
}