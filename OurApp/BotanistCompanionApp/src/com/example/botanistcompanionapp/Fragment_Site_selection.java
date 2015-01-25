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

	private Button sNext;
	private View view;
	private String email;
	private String name;
	private String phone;
	private Record rec;
	private Button pick;
	private PickRecordCommunicator mCallback;
	private List<Record> recordlist;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__site_selection, container, false);
		//Select_Plant = new Fragment_Select_Plant();
		
		rec = new Record();
		recordlist = mCallback.getRecordlist();
		
		pick = (Button) view.findViewById(R.id.Pick);
		pick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mCallback.LaunchRecordedit(rec);
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