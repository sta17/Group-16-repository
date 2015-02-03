package uk.ac.aber.dcs.cs22120.group16.main.fragments;

import uk.ac.aber.dcs.cs22120.group16.main.R;
import uk.ac.aber.dcs.cs22120.group16.main.recordpackage.Record;
//import uk.ac.aber.dcs.cs22120.group16.utilities.GPSLocation;





import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
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
 * @since 1.3
 * @version 2.2
 * @see NewRecord
 * @see EditRecord
 * @see FragmentComment
 * 
 * This is basically the GPS screen, in here is pretty much all needed to handle the GPS screen.
 *
 */
public class FragmentGPS extends Fragment {

	private int id;
	private Record rec;
	private Button GBack;
	private Button GNext;
	private Button GPSbutton;
	private View view;
	private String email;
	private String name;
	private String phone;
	private Location loc;
	private GPSCommunicator mCallback;
	private FragmentComment commentFragment;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__gps, container, false);
		commentFragment = new FragmentComment();
		rec = new Record();

		Bundle args = getArguments();
		if (args != null) {
			if (args.containsKey("RECORD")) {
				rec = (Record) args.getSerializable("RECORD");
				name = args.getString("NAME");
				phone = args.getString("PHONE");
				email = args.getString("EMAIL");
				id = args.getInt("ID");
			}
		}

		SetupButtons();
		return view;
	}

	/**
	 * 
	 * @author Steven(Sta17)
	 * 
	 * basically sets up the buttons on the page, are on their own for reason of code modulisation and ease of view.
	 * 
	 */
	private void SetupButtons(){
		
		GNext = (Button) view.findViewById(R.id.GNext);
		GNext.setOnClickListener(new OnClickListener() {
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
				commentFragment.setArguments(args);
				transaction.replace(id, commentFragment);
				transaction.addToBackStack(null);
				transaction.commit();	
			}
		});

		GBack = (Button) view.findViewById(R.id.GBack);
		GBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// gps.stopGPS();
				getFragmentManager().popBackStack();
			}
		});

		GPSbutton = (Button) view.findViewById(R.id.GPS_Button);
		GPSbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!mCallback.getGPSPossible()) {
					GPSnotEnableAlert();
				} else {
					loc = mCallback.getLocation();
					if (loc != null) {
						Toast.makeText(getActivity().getApplicationContext(),
								"GPS Coordinates aquired.", Toast.LENGTH_SHORT)
								.show();
						// GPS returns Null if no location was found thus
						// either:
						// either if you don't activate GPS, or you get bad
						// reception
						Double lat = loc.getLatitude();
						if (lat != null) {
							rec.setLatitude(lat);
						}
						Double lon = loc.getLongitude();
						if (lon != null) {
							rec.setLongitude(lon);
						}
						Toast.makeText(
								getActivity().getApplicationContext(),
								"GPS Coordinates: Latitude: " + lat
										+ " Longtitude: " + lon,
								Toast.LENGTH_SHORT).show();
					} else if (loc == null) {
						Toast.makeText(getActivity().getApplicationContext(),
								"Could not get GPS Cordinates",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	/**
	 * @author Steven(Sta17)
	 * 
	 * Container Activity must implement this interface. This interface 
	 * makes sure that the activity and fragment can communicate and thus 
	 * the fragment get the GPS coordinates.
	 *
	 */
	public interface GPSCommunicator {
		public Location getLocation();
		public boolean getGPSPossible();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (GPSCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement GPSCommunicator");
		}
	}

	/**
     * Basically launches a dialog window if GPS is not enabled.
     * 
     */
    public void GPSnotEnableAlert(){
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
        dlgAlert.setTitle("Enable GPS");
        dlgAlert.setMessage("GPS is not enabled, do you want to go to settings to enable GPS?");
        
        dlgAlert.setPositiveButton("GPS settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
            }
        });
  
        dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        dlgAlert.show();
    }
	
}