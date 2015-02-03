package uk.ac.aber.dcs.cs221.group16.main;

import uk.ac.aber.dcs.cs221.group16.util.PreventScreenRotation;
import uk.ac.aber.dcs.cs22120.group16.main.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 *                     Copyright(c)2013 Aberystwyth University. All rights
 *                     reserved.
 * 
 * @author Steven(Sta17),
 * @author Dan(Daf16)
 * @since 1.0
 * @version 2.2
 * @see EditRecord
 * @see NewRecord
 * @see SendDatabase
 * 
 *      Main class for starting app up
 *
 */
public class MainActivity extends Activity {

	private static final String PREFS_NAME = "BOTANIST";
	private Button newRecord;
	private Button editRecord;
	private Button sendtodatabase;

	/**
	 * creates the activity, and starts setups
	 * 
	 * @param savedInstanceState
	 *            the bundle basically
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnNewButton();
		getActionBar().setTitle("Overview");
		//prevents the activity from rotating
		@SuppressWarnings("unused")
		PreventScreenRotation freeze = new PreventScreenRotation(this);
		// Firsttime();
	}

	/**
	 * Basically sets up the buttons on the page, are on their own for reason of
	 * code modulisation and ease of view.
	 * 
	 * @see EditRecord
	 * @see NewRecord
	 * @see SendDatabase
	 */
	private void addListenerOnNewButton() {
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

	/**
	 * Is the popup message with instructions for how the app works. Decapricate
	 * by a readme file to be made.
	 */
	@SuppressWarnings("unused")
	private void Firsttime() {
		// Get from the SharedPreferences
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences(PREFS_NAME, 0);
		boolean firsttime = settings.getBoolean("FIRSTTIME", true);

		// Message here
		if (firsttime) {
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
			String newline = System.getProperty("line.separator");
			dlgAlert.setMessage("Click on the New Record button to start a new Recording, to save and attempt "
					+ "an upload go to comment button and click done."
					+ newline
					+ newline
					+ "To edit or delete a recording, please pick edit record, "
					+ "then pick the record and what you would like to do, for deletion, you will be given a comfirmation button."
					+ " To make a change final and attempt an upload go to comment button and click done."
					+ newline
					+ newline
					+ "the Send to Database will attempt to upload the records on the phone."
					+ newline
					+ newline
					+ "Click on the New Record button to start a new Recording,"
					+ newline
					+ newline
					+ "Your user details will be saved, name, phone number, email");
			dlgAlert.setTitle("Instructions");
			dlgAlert.setPositiveButton("OK", null);
			dlgAlert.setCancelable(true);
			dlgAlert.create().show();
			dlgAlert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							SharedPreferences settings = getApplicationContext()
									.getSharedPreferences(PREFS_NAME, 0);
							SharedPreferences.Editor editor = settings.edit();
							editor.putBoolean("FIRSTTIME", false);
							editor.commit();
							dialog.dismiss();
						}
					});
		}
	}
}