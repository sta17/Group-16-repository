package uk.ac.aber.dcs.cs22120.group16.activities;

import com.example.botanistcompanionapp.R;
import com.example.botanistcompanionapp.R.id;
import com.example.botanistcompanionapp.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

 
public class MainActivity extends Activity {
 
	private static final String PREFS_NAME = "BOTANIST";
	private Button newRecord;
	private Button editRecord;
	private Button sendtodatabase;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnNewButton();
		getActionBar().setTitle("Overview");

		// Get from the SharedPreferences
		SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
		boolean firsttime = settings.getBoolean("FIRSTTIME", true);
		
		//Message here
		
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("FIRSTTIME", false);
		editor.commit();
		
	}
 
	public void addListenerOnNewButton() {
 
		final Context context = this;
 
		newRecord = (Button) findViewById(R.id.newRecord);
		newRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, NewRecord.class);
                startActivity(intent);   
			}
		});
 
		editRecord = (Button) findViewById(R.id.editRecord);
		editRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, EditRecord.class);
                startActivity(intent);   
			}
		});
		
		sendtodatabase = (Button) findViewById(R.id.sendtodatabase);
		sendtodatabase.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, SendDatabase.class);
                startActivity(intent);   
			}
		});
		
	}
}