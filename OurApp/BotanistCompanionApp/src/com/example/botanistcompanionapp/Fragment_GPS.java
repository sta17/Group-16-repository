package com.example.botanistcompanionapp;

import recordPackage.Record;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Fragment_GPS extends Fragment {

	private Record rec;
	private Button GBack;
	private Button GDone;
	private Button GPSbutton;
	private View view;
	private String email;
	private String name;
	private String phone;
	private NewRecordCommunicator mCallback;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__gps, container, false);
		rec = new Record();

		Bundle args = getArguments();
		if (args != null) {
			if (args.containsKey("RECORD")) {
				rec = (Record) args.getSerializable("RECORD");
				name = args.getString("NAME");
				phone = args.getString("PHONE");
				email = args.getString("EMAIL");
			}
		}
		
 		GDone = (Button) view.findViewById(R.id.GDone);
 		GDone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mCallback.ReturnFinalRecord(rec,email,name,phone);
			} });

 		GBack = (Button) view.findViewById(R.id.GBack);
 		GBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getFragmentManager().popBackStack();
			}
		});
 		
 		GPSbutton = (Button) view.findViewById(R.id.GPS_Button);
 		GPSbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Location loc = mCallback.getLocation();
				if(loc != null){
					//Toast.makeText(getActivity().getApplicationContext(), "GPS Coordinates aquired.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(getActivity().getApplicationContext(), "GPS Coordinates: Latitude: "+lat+" Longtitude: "+lon, Toast.LENGTH_SHORT).show();
				} else if(loc == null){
					Toast.makeText(getActivity().getApplicationContext(), "Could not get GPS Cordinates", Toast.LENGTH_SHORT).show();
				}
			} });
 		
		return view;
	}

    // Container Activity must implement this interface
    public interface NewRecordCommunicator {
        public void ReturnFinalRecord(Record temp,String Email,String Name, String Phone);
        public Location getLocation();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (NewRecordCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NewRecordCommunicator");
        }
    }
	
}