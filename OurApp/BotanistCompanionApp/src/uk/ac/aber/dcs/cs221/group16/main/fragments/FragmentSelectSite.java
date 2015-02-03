package uk.ac.aber.dcs.cs221.group16.main.fragments;

import uk.ac.aber.dcs.cs22120.group16.main.R;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs221.group16.util.StringAdapter;
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
public class FragmentSelectSite extends Fragment {

	private FragmentSelectPlant siteselectionFragment;
	private Button SSNext;
	private Button SSBack;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	private SiteListCommunicator mCallback;
	private ArrayList<String> sitelist;
	private int id;
	private View view;
	private StringAdapter listadapter;
	private ListView SSListView;
	private TextView SiteDisplay;
	
	/**
	 * Creates a view populated by the fragment_select_site xml file and adds
	 * buttons, data and functionality to this view.
	 * @param inflater 
	 * @param container
	 * @param saved instance
	 * @return view
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__select__site,
	            container, false);
		SiteDisplay = (TextView) view.findViewById(R.id.SSSiteDisplay);
		siteselectionFragment = new FragmentSelectPlant();
		rec = new Record();
		sitelist = mCallback.getSitelist();
		Bundle args0 = getArguments();
		if(args0 != null){
		if(args0.containsKey("RECORD")){
			rec = (Record) args0.getSerializable("RECORD");
			name = args0.getString("NAME");
			phone = args0.getString("PHONE");
			email = args0.getString("EMAIL");
			id = args0.getInt("ID");
			String input = rec.getSiteName();
			if(input != null){
				SiteDisplay.setText(input);
			}
		}}
		
		SetupButtons();
		
		//mainListView = (ListView) findViewById( R.id.PSlistView );
		
		//View view = inflater.inflate(R.layout.fragment_plant_selection, container, false);
		SSListView = (ListView) view.findViewById( R.id.SSlistView ); 
				
				//add items to list rows
				
			    //add each row (item) to list
			    listadapter = new StringAdapter(getActivity(), sitelist);
			    
			    SSListView.setAdapter(listadapter);
			    
			    //set what happens when an item in the list is clicked. A variable "plant" is given the text (name) 
			    //of the plant (listItem)
			    SSListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							rec.setSiteName(sitelist.get((int) id));
							SiteDisplay.setText(rec.getSiteName());
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
		
		SSNext = (Button) view.findViewById(R.id.SSNext);
		SSNext.setOnClickListener(new OnClickListener() {
			
			/**
			 * 
			 * Goes to next page(plant), stores values as well as putting this fragment on top of
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
				siteselectionFragment.setArguments(args);
				transaction.replace(id, siteselectionFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
			
		});
		
		SSBack = (Button) view.findViewById(R.id.SSBack);
		SSBack.setOnClickListener(new OnClickListener() {
			
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
    public interface SiteListCommunicator {
        public ArrayList<String> getSitelist();
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
            mCallback = (SiteListCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SiteListCommunicator");
        }
    }
}