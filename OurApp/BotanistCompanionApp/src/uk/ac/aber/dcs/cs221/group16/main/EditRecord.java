package uk.ac.aber.dcs.cs221.group16.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentRecordSelection;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentComment.RecordCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentGps.GPSCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentRecordSelection.PickRecordCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentSelectPlant.PlantListCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentSelectSite.SiteListCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.plantpackage.Plant;
import uk.ac.aber.dcs.cs221.group16.main.plantpackage.PlantListInteracter;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.RecordManagement;
import uk.ac.aber.dcs.cs221.group16.util.GPSLocation;
import uk.ac.aber.dcs.cs221.group16.util.PreventScreenRotation;
import uk.ac.aber.dcs.cs22120.group16.main.R;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17),
 * @author Dan(Daf16)
 * @since 1.5
 * @version 2.2
 * @see FragmentRecordSelection
 * 
 * This is class that sets up the user actions for editing and deleting a record.
 *
 */
public class EditRecord extends FragmentActivity 
implements RecordCommunicator, PlantListCommunicator, PickRecordCommunicator, GPSCommunicator,SiteListCommunicator {

	private static final String PREFS_NAME = "BOTANIST";
	private PlantListInteracter plantlist;
	private RecordManagement recordmanager;
	private FragmentRecordSelection Site_selection;
	private GPSLocation gps;
	private Location loc;
	private String email;
	private String name;
	private String phone;
	private List<Record> recordlist;
	private List<String> sitelist;
	private FragmentTransaction ft;
	private FragmentManager fm;
	private int recordnumber;
	private Record original;
	private int index;
	
	/**
	 * creates an view/screen and adds all needed, listeners, buttons etc, basically a constructor.
	 * 
	 * @param savedInstanceState the bundle basically
	 * @see FragmentRecordSelection
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		EditRecord(savedInstanceState);

		getActionBar().setTitle("Edit or delete a Record");
		//prevents the activity from rotating
		@SuppressWarnings("unused")
		PreventScreenRotation freeze = new PreventScreenRotation(this);

		// saves the details for sending to fragment
		Bundle args1 = new Bundle();
		args1.putString("NAME", name);
		args1.putString("EMAIL", email);
		args1.putString("PHONE", phone);
		args1.putInt("ID", R.id.fragment_edit_container);

		// starts the first fragment
		Site_selection = new FragmentRecordSelection();
		Site_selection.setArguments(getIntent().getExtras());
		Site_selection.setArguments(args1);
		fm = getSupportFragmentManager(); // Transaction start
		ft = fm.beginTransaction().add(R.id.fragment_edit_container,
				Site_selection);
		ft.commit();
	}

	/**
	 * Basically just initiates most of the variables, 
	 * i separated this out for ease of view and 
	 * Clarity in the code.
	 * 
	 * @param savedInstanceState the bundle basically
	 */
	private void EditRecord(Bundle savedInstanceState) {
		recordmanager = new RecordManagement();
		plantlist = new PlantListInteracter();
		setContentView(R.layout.activity_edit_record);// error bit

		if (findViewById(R.id.fragment_edit_container) != null) {
			if (savedInstanceState != null) {
				return;
			}
		}
		gps = new GPSLocation(this);

		// Get from the SharedPreferences
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences(PREFS_NAME, 0);
		email = settings.getString("EMAIL", null);
		name = settings.getString("NAME", null);
		phone = settings.getString("PHONE", null);

		plantlist = tempPlantListCreator(); // HARD CODED BIT IS HERE REMOVE
											// WHEN TESTING DATABASE
		
		
		try { // tried to load from file, if not autogenerate replacement file
			boolean bool = recordmanager.Tempload(this);
			if(!bool){
				recordmanager = tempRecordListCreator(); // HARD CODED BIT IS HERE
														 // REMOVE WHEN TESTING
														 // DATABASE
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
		/*
		recordmanager = tempRecordListCreator(); // HARD CODED BIT IS HERE
		 										 // REMOVE WHEN TESTING
		 										 // DATABASE
		*/
		recordlist = recordmanager.getAllRecords();
		sitelist = tempSiteListCreator();
	}

	/**
	 * Initialize the contents of the Activity's standard options menu. You should place your menu items in to menu. 
	 * 
	 * @param menu class to intiate to.
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_record, menu);
		return true;
	}

	/**
	 * This hook is called whenever an item in your options menu is selected. 
	 * The default implementation simply returns false to have the normal processing happen 
	 * (calling the item's Runnable or sending a message to its Handler as appropriate). 
	 * You can use this method for any items for which you would like to do processing without
	 * those other facilities. 
	 * 
	 * Derived classes should call through to the base class for it to perform the default menu handling.
	 *
	 * 
	 * @param The menu item that was selected.
	 */
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

	/**
	 * Provides the GPS location to fragments
	 * 
	 * @return loc gps location in a location class
	 */
	public Location getLocation() {
		loc = gps.getLocation();
		return loc;
	}

	/**
	 * provides the GPS status, basically
	 * is it possible to get GPS coordinates.
	 * if true, gps is fine
	 * if false, gps coordinates cannot be gotten
	 * 
	 * @return status returns a true or false signal depending 
	 * 
	 */
	@Override
	public boolean getGPSPossible() {
		boolean status = gps.getGPSPossible();
		return status;
	}

	/**
	 * Stops the GPS, a method 
	 * for the fragments.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		gps.stopGPS();
	}

	/**
	 * constructs a list of plants 
	 * a speedy generator
	 * 
	 * @return temp a plant list
	 */
	public PlantListInteracter tempPlantListCreator() {
		PlantListInteracter temp = new PlantListInteracter();
		temp.addplant("Chara aculeolata", "Hedgehog Stonewort");
		temp.addplant("Chara aspera", "Rough Stonewort");
		temp.addplant("Chara baltica", "Baltic Stonewort");
		temp.addplant("Chara braunii", "Braun's Stonewort");
		temp.addplant("Isoetes histrix", "Land Quillwort");
		temp.addplant("Equisetum hyemale", "Rough Horsetail");
		temp.addplant("Equisetum fluviatile", "Water Horsetail");
		temp.addplant("Pteris vittata", "Ladder Brake");
		temp.addplant("Cyathea dealbata", "Silver Tree-fern");
		temp.addplant("Pteridium aquilinum", "Bracken");
		temp.addplant("Phyllitis scolopendrium", "Hart's-tongue ");
		return temp;
	}

	/**
	 * constructs a list of sites 
	 * a speedy generator
	 * 
	 * @return temp a sites list
	 */
	public ArrayList<String> tempSiteListCreator() {
		ArrayList<String> temp = new ArrayList();
		temp.add("Peak District");
		temp.add("Lake District");
		temp.add("Snowdonia");
		temp.add("Dartmoor");
		temp.add("Pembrokeshire Coast");
		temp.add("North York Moors");
		temp.add("Yorkshire Dales");
		temp.add("Exmoor");
		temp.add("Northumberland");
		temp.add("Brecon Beacons");
		temp.add("The Broads");
		temp.add("New Forest");
		temp.add("South Downs");
		return temp;
	}
	
	/**
	 * constructs a list of record 
	 * a speedy generator
	 * 
	 * @return temp a record list
	 */
	public RecordManagement tempRecordListCreator() {
		RecordManagement temp = new RecordManagement();
		for (int i = 0; i < 10; i++) {
			Record rec = new Record();
			String date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
			rec.setRecordname(""+i);
			rec.setEditdate(date);
			rec.setComment("bla bla" + i);
			rec.setLatitude((14 + i));
			rec.setLatitude((20 + i));
			rec.setPlantCommon("plantcommon " + i);
			rec.setPlantLatin("plantlatin " + i);
			rec.setRecordname("" + i);
			rec.setUploaded(false);
			rec.setDAFOR(Record.DAFORLEVEL.Dominant);
			temp.editARecord(rec);
			temp.addRecordToList();
		}
		return temp;
	}

	/**
	 * This is the final method that is to end the activity and handle all other clean up.
	 * 
	 * @param rec the recording that have been edited that is to be edited
	 * @param email that is supposed to be saved
	 * @param name that is supposed to be saved
	 * @param phone that is supposed to be saved
	 */
	@Override
	public void ReturnFinalRecord(Record rec, String email, String name, String phone) {
		this.email = email;
		this.name = name;
		this.phone = phone;

		// Get from the SharedPreferences
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences(PREFS_NAME, 0);
		recordnumber = settings.getInt("RECORDNUMBER", 0);

		String date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		rec.setEditdate(date);
		rec.setRecordname("recordnumber");
		recordnumber++;

		SharedPreferences.Editor editor = settings.edit();
		editor.putString("EMAIL", email);
		editor.putString("PHONE", phone);
		editor.putString("NAME", name);
		editor.putInt("RECORDNUMBER", recordnumber);
		editor.commit();

		recordmanager.editARecord(rec);
		recordmanager.addRecordToList();
		
		try {
			recordmanager.TempSave(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finish();
	}

	/**
	 * Gets a list of plants from the plantlistInteractor class
	 * 
	 * @return plantlist.getAllPlants() is the list of plants stored
	 */
	@Override
	public ArrayList<Plant> getPlantlist() {
		return plantlist.getAllPlants();
	}

	/**
	 * get a list of records from RecordManagement class
	 * 
	 * @return recordlist the list of the records
	 */
	@Override
	public List<Record> getRecordlist() {
		recordlist = recordmanager.getAllRecords();
		if(original != null){
			recordlist.remove(index);
			recordlist.add(original);
		}
		return recordlist;
	}

	/**
	 * save original status of the record that is to be edited, 
	 * for later reinsertions if the users don't finish their
	 * editing.
	 * 
	 * @param rec saves an original edition of the record to be edited
	 * @param index is the index number of the arraylist position
	 * 
	 */
	@Override
	public void saveOriginal(Record rec, int index) {
		this.original = rec;
		this.index = index;
	}

	/**
	 * sets the original and index to null to prevent
	 * it from being remembered
	 */
	@Override
	public void wipeOriginal() {
		this.original = null;
		this.index = (Integer) null;
	}

	@Override
	public ArrayList<String> getSitelist() {
		return (ArrayList<String>) sitelist;
	}

}