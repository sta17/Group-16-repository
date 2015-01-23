package com.example.the_botonists_companion;

import recordPackage.Record;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView commenttarget;
	private TextView emailtarget;
	private TextView latitudetarget;
	private TextView nametarget;
	private TextView phonetarget;
	private ImageView plant;
	private ImageView area;
	private Record rec;

	public void createButtons() {
		Button userdetails = (Button) findViewById(R.id.userdetails);
		userdetails.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						UserDetails.class);
				startActivityForResult(myIntent, 0);
			}

		});

		Button next = (Button) findViewById(R.id.toCamera);
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), Camera.class);
				myIntent.putExtra("RECORD", rec);
				startActivityForResult(myIntent, 0);
			}

		});
	}

	public void modifyFields() {
		// taking the textview1 reference
		nametarget = (TextView) findViewById(R.id.nametargetfield);
		phonetarget = (TextView) findViewById(R.id.phonetargetfield);
		emailtarget = (TextView) findViewById(R.id.emailtargetfield);
		commenttarget = (TextView) findViewById(R.id.commenttargetfield);
		latitudetarget = (TextView) findViewById(R.id.latitudetargetfield);
		plant = (ImageView) findViewById(R.id.planttemptest);
		area = (ImageView) findViewById(R.id.areatemptest);

		Bundle bunble = getIntent().getExtras();
		if (bunble != null) {
			// Getting the value stored in the name "NAME"
			String user_name = bunble.getString("NAME");
			String user_email = bunble.getString("EMAIL");
			String user_phone = bunble.getString("PHONE");
			rec = (Record) bunble.getSerializable("RECORD");
			// appending the value to the contents of textView1.

			nametarget.append(" " + user_name);
			phonetarget.append(" " + user_phone);
			emailtarget.append(" " + user_email);
			latitudetarget.append(" " + rec.getLatitude());
			commenttarget.append(" " + rec.getComment());
			
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createButtons();
		modifyFields();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		data.getExtras();
		if (data != null) {
			rec = (Record) data.getSerializableExtra("RECORD");

			if(rec.getSpecimenIMGPath() != null){
				String plantpath = rec.getSpecimenIMGPath();
				Bitmap bm2;
				BitmapFactory.Options btmapOptions2 = new BitmapFactory.Options();
				bm2 = BitmapFactory.decodeFile(plantpath, btmapOptions2);
				plant.setImageBitmap(bm2);
			}
			
			if(rec.getSceneIMGPath() != null){
				String areapath = rec.getSceneIMGPath();
				Bitmap bm1;
				BitmapFactory.Options btmapOptions1 = new BitmapFactory.Options();
				bm1 = BitmapFactory.decodeFile(areapath, btmapOptions1);
				area.setImageBitmap(bm1);
				
			}
		}
	}
	
}
