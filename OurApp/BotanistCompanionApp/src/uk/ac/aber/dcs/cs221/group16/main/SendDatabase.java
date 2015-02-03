package uk.ac.aber.dcs.cs221.group16.main;

import uk.ac.aber.dcs.cs221.group16.util.PreventScreenRotation;
import uk.ac.aber.dcs.cs22120.group16.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17)
 * @since 1.5
 * @version 2.2
 * 
 * Is supposed to serve as a Uploading button to do the uploading of 
 * records incase of failure at end of EditRecord and NewRecord
 *
 */
public class SendDatabase extends Activity {

	/**
	 * creates the activity, and starts setups
	 * 
	 * @param savedInstanceState the bundle basically
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_database);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_database, menu);
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
}
