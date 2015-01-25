package com.example.botanistcompanionapp;

import java.util.ArrayList;

import recordPackage.Record;
import recordPackage.Record.DAFORLEVEL;
import utilities.Plant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

 
public class Fragment_Select_Plant extends Fragment {

	private final static String ARG_POSITION = "position";
	private Fragment DAFORFragment;
	private Button pNext;
	private Button pBack;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	private PlantListCommunicator mCallback;
	private ArrayList<Plant> plantlist;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment__select__plant,
	            container, false);
		DAFORFragment = new Fragment_DAFOR();
		rec = new Record();
		plantlist = mCallback.getPlantlist();
		Bundle args0 = getArguments();
		if(args0 != null){
		if(args0.containsKey("RECORD")){
			rec = (Record) args0.getSerializable("RECORD");
			name = args0.getString("NAME");
			phone = args0.getString("PHONE");
			email = args0.getString("EMAIL");
		}}
		
		pNext = (Button) view.findViewById(R.id.PSNext);
		pNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Bundle args = new Bundle();
				args.putSerializable("RECORD", rec);
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				DAFORFragment.setArguments(args);
				transaction.replace(R.id.fragment_container, DAFORFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
			
		});
		
		pBack = (Button) view.findViewById(R.id.PSBack);
		pBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getFragmentManager().popBackStack();
			} });
		
		return view;
	}

	// Container Activity must implement this interface
    public interface PlantListCommunicator {
        public ArrayList<Plant> getPlantlist();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (PlantListCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PlantListCommunicator");
        }
    }
}