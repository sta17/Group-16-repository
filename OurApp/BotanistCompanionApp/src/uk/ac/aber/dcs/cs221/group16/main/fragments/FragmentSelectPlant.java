package uk.ac.aber.dcs.cs221.group16.main.fragments;

import uk.ac.aber.dcs.cs221.group16.main.plantpackage.Plant;
import uk.ac.aber.dcs.cs221.group16.main.plantpackage.PlantAdapter;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs22120.group16.main.R;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
 
/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17),
 * @author Dan(Daf16)
 * @since 1.2
 * @version 2.2
 * @see NewRecord
 * @see EditRecord
 * @see FragmentDafor
 * 
 * This is the Select plant screen, in here is pretty much all needed to handle the select plant screen.
 *
 */
public class FragmentSelectPlant extends Fragment {

	private FragmentDafor DAFORFragment;
	private Button pNext;
	private Button pBack;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	private PlantListCommunicator mCallback;
	private ArrayList<Plant> plantlist;
	private int id;
	private View view;
	private PlantAdapter listadapter;
	private ListView plantListView;
	private TextView plantdisplay;
	
	/**
	 * Creates a view populated by the fragment_select_plant xml file and adds
	 * buttons, data and functionality to this view.
	 * @param inflater 
	 * @param container
	 * @param saved instance
	 * @return view
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__select__plant,
	            container, false);
		plantdisplay = (TextView) view.findViewById(R.id.PlantDisplay);
		DAFORFragment = new FragmentDafor();
		rec = new Record();
		plantlist = mCallback.getPlantlist();
		Bundle args0 = getArguments();
		if(args0 != null){
		if(args0.containsKey("RECORD")){
			rec = (Record) args0.getSerializable("RECORD");
			name = args0.getString("NAME");
			phone = args0.getString("PHONE");
			email = args0.getString("EMAIL");
			id = args0.getInt("ID");
			String input = rec.getPlantLatin();
			if(input != null){
				plantdisplay.setText(input);
			}
		}}
		
		SetupButtons();
		
		//mainListView = (ListView) findViewById( R.id.PSlistView );
		
		//View view = inflater.inflate(R.layout.fragment_plant_selection, container, false);
				plantListView = (ListView) view.findViewById( R.id.PSlistView ); 
				
				//add items to list rows
				
			    //add each row (item) to list
			    listadapter = new PlantAdapter(getActivity(), plantlist);
			    
			    plantListView.setAdapter(listadapter);
			    
			    //set what happens when an item in the list is clicked. A variable "plant" is given the text (name) 
			    //of the plant (listItem)
			    plantListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							rec.setPlantCommon(plantlist.get((int) id).getCommon());
							rec.setPlantLatin(plantlist.get((int) id).getLatin());
							plantdisplay.setText(rec.getPlantLatin());
					}
		        });
		
		return view;
	}

	/**
	 * 
	 * 
	 * This method sets up the buttons on the page, which are on their own for reason of code modulisation
	 *  and ease of view.
	 * 
	 */
	private void SetupButtons() {
		
		pNext = (Button) view.findViewById(R.id.PSNext);
		pNext.setOnClickListener(new OnClickListener() {
			
			/**
			 * 
			 * Goes to next page(dafor), stores values as well as putting this fragment on top of
			 * the stack for later popback functionality
			 * @param arg0 (view which is clicked)
			 */
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
				DAFORFragment.setArguments(args);
				transaction.replace(id, DAFORFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
			
		});
		
		pBack = (Button) view.findViewById(R.id.PSBack);
		pBack.setOnClickListener(new OnClickListener() {
			
			/**
			 * 
			 * Makes it so that when the back button is pressed it pops the previous fragment
			 * of the stack and replaces this fragment with it.
			 * @param arg0 (view which is clicked)
			 */
			@Override
			public void onClick(View arg0) {
				getFragmentManager().popBackStack();
			} });
	}
	
	/**
	 *
	 *Container Activity must implement this interface. 
	 *This interface makes sure that the activity class 
	 *has the methods needed to get the plant list from the database
	 *and return them to the fragment which is to make a display list 
	 *out of it.
	 *
	 */
    public interface PlantListCommunicator {
        public ArrayList<Plant> getPlantlist();
    }

    /**
     * Puts the fragment on to the activity
     */
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