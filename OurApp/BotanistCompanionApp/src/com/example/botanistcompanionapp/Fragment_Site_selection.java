package com.example.botanistcompanionapp;

import java.util.List;

import recordPackage.Record;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Fragment_Site_selection extends Fragment {

	private Fragment_Userdata fragment_userdata;
	private Button sNext;
	private View view;
	private String email;
	private String name;
	private String phone;
	private Record rec;
	private Button pick;
	private PickRecordCommunicator mCallback;
	private List<Record> recordlist;
	private int id;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__site_selection, container, false);
		//Select_Plant = new Fragment_Select_Plant();
		
		fragment_userdata = new Fragment_Userdata();
		rec = new Record();
		recordlist = mCallback.getRecordlist();
		Bundle args = getArguments();
		name = args.getString("NAME");
		phone = args.getString("PHONE");
		email = args.getString("EMAIL");
		id = args.getInt("ID");
		
		
		pick = (Button) view.findViewById(R.id.Pick);
		pick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//HARDCODED BIT START
				rec.setComment("bla bla");
				rec.setLatitude(14);
				rec.setLatitude(20);
				rec.setPlantCommon("plantcommon");
				rec.setPlantLatin("plantlatin");
				rec.setRecordname("0");
				rec.setUploaded(false);
				//HARDCODED BIT END
				
				mCallback.LaunchRecordedit(rec);
				
				Bundle args1 = new Bundle();
				args1.putSerializable("RECORD", rec);
				args1.putString("NAME", name);
				args1.putString("EMAIL", email);
				args1.putString("PHONE", phone);
				args1.putInt("ID", id);
				
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				fragment_userdata.setArguments(args1);
				transaction.replace(id, fragment_userdata);
				transaction.addToBackStack(null);
				transaction.commit();	
				
			} });
		
		return view;
	}	

	// Container Activity must implement this interface
    public interface PickRecordCommunicator {
    	public void LaunchRecordedit(Record rec);
    	public List<Record> getRecordlist();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (PickRecordCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PickRecordCommunicator");
        }
    }
}