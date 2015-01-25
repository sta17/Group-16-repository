package com.example.botanistcompanionapp;

import java.util.ArrayList;

import plantPackage.Plant;
import plantPackage.PlantListInteracter;

import com.example.botanistcompanionapp.Fragment_GPS.NewRecordCommunicator;
import com.example.botanistcompanionapp.Fragment_Select_Plant.PlantListCommunicator;

import recordPackage.Record;
import recordPackage.RecordManagement;
import utilities.GPSLocation;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class NewRecord extends FragmentActivity implements NewRecordCommunicator, PlantListCommunicator {
	
	private static final String PREFS_NAME = "BOTANIST";
	private PlantListInteracter plantlist;
	private RecordManagement recordmanager;
	private Fragment_Userdata firstFragment;
	private Record rec = null;
	private GPSLocation gps;
	private Location loc;
	private String email;
	private String name;
	private String phone;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		firstFragment = new Fragment_Userdata();
		recordmanager = new RecordManagement();
		plantlist = new PlantListInteracter();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_record);//error bit
		rec = new Record();
		gps = new GPSLocation(this);
		gps.startGPS();
		
		// Get from the SharedPreferences
		SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
		email = settings.getString("EMAIL", null);
		name = settings.getString("NAME", null);
		phone = settings.getString("PHONE", null);
		
		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			} }
		
		loc = getLocation();
		if(loc != null){
			Toast.makeText(this.getApplicationContext(), "GPS Coordinates aquired.", Toast.LENGTH_SHORT).show();
			// GPS returns Null if no location was found thus either:
			// either if you don't activate GPS, or you get bad reception
			Double lat = loc.getLatitude();
			if(lat != null){
				rec.setLatitude(lat);
			}
			Double lon = loc.getLongitude();
			if(lon != null){
				rec.setLongitude(lon);
			}
		}
		
		Bundle args1 = new Bundle();
		args1.putSerializable("RECORD", rec);
		args1.putString("NAME", name);
		args1.putString("EMAIL", email);
		args1.putString("PHONE", phone);
		args1.putInt("ID", R.id.fragment_container);
		
		firstFragment = new Fragment_Userdata();
		firstFragment.setArguments(getIntent().getExtras());
		firstFragment.setArguments(args1);
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

		getActionBar().setTitle("Creating a New Record");
		
		plantlist = tempListCreator();
		
	}
	
	public Location getLocation(){
		loc = gps.getLocation();
		return loc;
	}
	
	@Override
	protected void onStop (){
		super.onStop();
		gps.stopGPS();
	}

	public PlantListInteracter tempListCreator(){
		PlantListInteracter temp = new PlantListInteracter();
		for(int i = 0; i < 10; i++){
			temp.addplant("latin"+i, "common"+i);
		}
		return temp;
	}
	
	/**
	 * this is the final method that is to end the activity.
	 */
	@Override
	public void ReturnFinalRecord(Record rec,String email,String name, String phone) {
		this.rec = rec;
		this.email = email;
		this.name = name;
		this.phone = phone;
		
		SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("EMAIL", email);
		editor.putString("PHONE", phone);
		editor.putString("NAME", name);
		editor.commit();
		
		recordmanager.editARecord(rec);
		recordmanager.addRecordToList();
		recordmanager.save();
		
		finish();
	}

	@Override
	public ArrayList<Plant> getPlantlist() {
		return plantlist.getAllPlants();
	}
}
