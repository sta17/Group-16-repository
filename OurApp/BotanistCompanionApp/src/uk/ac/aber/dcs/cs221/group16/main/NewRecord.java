package uk.ac.aber.dcs.cs221.group16.main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentSelectSite.SiteListCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentUserInput;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentComment.RecordCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentGps.GPSCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.fragments.FragmentSelectPlant.PlantListCommunicator;
import uk.ac.aber.dcs.cs221.group16.main.plantpackage.Plant;
import uk.ac.aber.dcs.cs221.group16.main.plantpackage.PlantListInteracter;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.RecordManagement;
import uk.ac.aber.dcs.cs221.group16.util.GPSLocation;
import uk.ac.aber.dcs.cs221.group16.util.PreventScreenRotation;
import uk.ac.aber.dcs.cs22120.group16.main.R;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17),
 * @author Dan(Daf16)
 * @since 1.0
 * @version 2.2
 * @see FragmentUserInput
 * 
 * This is class that sets up the user actions for creating a new record.
 *
 */
public class NewRecord extends FragmentActivity 
implements RecordCommunicator, PlantListCommunicator,GPSCommunicator,SiteListCommunicator {

	private static final String PREFS_NAME = "BOTANIST";
	private PlantListInteracter plantlist;
	private RecordManagement recordmanager;
	private FragmentUserInput fragmentuserinput;
	private Record rec = null;
	private GPSLocation gps;
	private List<String> sitelist;
	private Location loc;
	private String email;
	private String name;
	private String phone;
	private int recordnumber;

	/**
	 * creates an view/screen and adds all needed, listeners, buttons etc, basically a constructor.
	 * 
	 * @param savedInstanceState the bundle basically
	 * @see FragmentUserInput
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		NewRecord(savedInstanceState);
		
		loc = getLocation();
		if (loc != null) {
			Toast.makeText(this.getApplicationContext(),
					"GPS Coordinates aquired.", Toast.LENGTH_SHORT).show();
			// GPS returns Null if no location was found thus either:
			// either if you don't activate GPS, or you get bad reception
			Double lat = loc.getLatitude();
			if (lat != null) {
				rec.setLatitude(lat);
			}
			Double lon = loc.getLongitude();
			if (lon != null) {
				rec.setLongitude(lon);
			}
		}

		//prevents the activity from rotating
		@SuppressWarnings("unused")
		PreventScreenRotation freeze = new PreventScreenRotation(this);

		// saves the details for sending to fragment
		Bundle args1 = new Bundle();
		args1.putSerializable("RECORD", rec);
		args1.putString("NAME", name);
		args1.putString("EMAIL", email);
		args1.putString("PHONE", phone);
		args1.putInt("ID", R.id.fragment_container);

		// starts the first fragment
		fragmentuserinput = new FragmentUserInput();
		fragmentuserinput.setArguments(getIntent().getExtras());
		fragmentuserinput.setArguments(args1);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, fragmentuserinput).commit();

		getActionBar().setTitle("Creating a New Record");

		plantlist = tempPlantListCreator();
		sitelist = tempSiteListCreator();
	}

	/**
	 * Basically just initiates most of the variables, 
	 * i separated this out for ease of view and 
	 * Clarity in the code.
	 * 
	 * @param savedInstanceState the bundle basically
	 */
	private void NewRecord(Bundle savedInstanceState) {
		fragmentuserinput = new FragmentUserInput();
		recordmanager = new RecordManagement();
		plantlist = new PlantListInteracter();
		setContentView(R.layout.activity_new_record);// error bit
		rec = new Record();
		rec.setDAFOR(Record.DAFORLEVEL.NOVALUE);
		
		
		try { // tried to load from file for later saving
			recordmanager.Tempload(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 

		gps = new GPSLocation(this);

		// Get from the SharedPreferences
		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences(PREFS_NAME, 0);
		email = settings.getString("EMAIL", null);
		name = settings.getString("NAME", null);
		phone = settings.getString("PHONE", null);

		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}
		}
	}
	
	/**
	 * Provides the GPS location to fragments
	 * 
	 * @return loc gps location in a location class
	 */
	@Override
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
	 * This is the final method that is to end the activity and handle all other clean up.
	 * 
	 * @param rec the recording that have been edited that is to be edited
	 * @param email that is supposed to be saved
	 * @param name that is supposed to be saved
	 * @param phone that is supposed to be saved
	 */
	@Override
	public void ReturnFinalRecord(Record rec, String email, String name,
			String phone) {
		this.rec = rec;
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

	@Override
	public ArrayList<String> getSitelist() {
		return (ArrayList<String>) sitelist;
	}

}