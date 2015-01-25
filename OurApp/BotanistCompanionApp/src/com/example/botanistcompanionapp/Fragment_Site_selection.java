package com.example.botanistcompanionapp;

import java.util.List;

import recordPackage.Record;
import recordPackage.RecordAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import plantPackage.Plant;
import plantPackage.PlantAdapter;
import recordPackage.Record.DAFORLEVEL;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Fragment_Site_selection extends Fragment {

	private Fragment_Userdata fragment_userdata;
	private View view;
	private String email;
	private String name;
	private String phone;
	private Record rec;
	private Button pick;
	private PickRecordCommunicator mCallback;
	private List<Record> recordlist;
	private int id;
	private RecordAdapter listadapter;
	private ListView recordListView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__site_selection, container, false);
		//Select_Plant = new Fragment_Select_Plant();
		
		fragment_userdata = new Fragment_Userdata();
		rec = null;
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
				if(rec != null){
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
			} else{
				Toast.makeText(getActivity().getApplicationContext(), "Please Select a Record to edit", Toast.LENGTH_SHORT).show();
			}
			} });
		
		//mainListView = (ListView) findViewById( R.id.PSlistView );
		
				//View view = inflater.inflate(R.layout.fragment_plant_selection, container, false);
						recordListView = (ListView) view.findViewById( R.id.RSlistView ); 
						
						//add items to list rows
						
					    //add each row (item) to list
					    listadapter = new RecordAdapter(getActivity(), recordlist);
					    
					    recordListView.setAdapter(listadapter);
					    
					    //set what happens when an item in the list is clicked. A variable "plant" is given the text (name) 
					    //of the plant (listItem)
					    recordListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
									rec = recordlist.get((int) id);
									selectRecord(rec);
							}
				        });
				
		
		return view;
	}	

	private void selectRecord(final Record temp) {
		final CharSequence[] items = { "Edit Record","Delete Record",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Edit Record")) {

					mCallback.LaunchRecordedit(temp);
					
					Bundle args1 = new Bundle();
					args1.putSerializable("RECORD", temp);
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

				} else if (items[item].equals("Delete Record")) {
					DeleteRecord(temp);
					dialog.dismiss();
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	private void DeleteRecord(final Record temp){
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			@Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            recordlist.remove(temp);
		            setRecNull();
		            listadapter.notifyDataSetChanged();
		            break;
		        case DialogInterface.BUTTON_NEGATIVE:
		            break;
		        }
		    }
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Are you sure you want to delete the record?").setPositiveButton("Yes", dialogClickListener)
		    .setNegativeButton("No", dialogClickListener).show();
	}
	
	private void setRecNull(){
		this.rec = null;
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