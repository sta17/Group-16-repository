package com.example.botanistcompanionapp;

import recordPackage.Record;
import recordPackage.Record.DAFORLEVEL;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Fragment_Userdata  extends Fragment {
 
	private final static String ARG_POSITION = "position";
	private Fragment_Select_Plant Select_Plant;
	private Button submit;
	private EditText emailfield;
	private EditText namefield;
	private EditText phonefield;
	private String email;
	private String name;
	private String phone;
	private Record rec;
	private View view;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__userdata, container, false);
		Select_Plant = new Fragment_Select_Plant();
		emailfield = (EditText) view.findViewById(R.id.edit_email);
		namefield = (EditText) view.findViewById(R.id.edit_name);
		phonefield = (EditText) view.findViewById(R.id.edit_phone);
		
		rec = new Record();
		Bundle args = getArguments();
		if(args != null){
		if(args.containsKey("RECORD")){
			rec = (Record) args.getSerializable("RECORD");
			name = args.getString("NAME");
			phone = args.getString("PHONE");
			email = args.getString("EMAIL");
		}}
		
		if (email != null) {
			emailfield.append(email);
		}
		if (phone != null) {
			phonefield.append(phone);
		}
		if (name != null) {
			namefield.append(name);
		}
			
		submit = (Button) view.findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				name = namefield.getText().toString();
				phone = phonefield.getText().toString();
				email = emailfield.getText().toString();
				Bundle args = new Bundle();
				args.putSerializable("RECORD", rec);
				args.putString("NAME", name);
				args.putString("EMAIL", email);
				args.putString("PHONE", phone);
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				Select_Plant.setArguments(args);
				transaction.replace(R.id.fragment_container, Select_Plant);
				transaction.addToBackStack(null);
				transaction.commit();	
			} });
		return view;
	}


}