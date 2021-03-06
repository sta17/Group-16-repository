package uk.ac.aber.dcs.cs22120.group16.main.fragments;

import uk.ac.aber.dcs.cs22120.group16.main.R;
import uk.ac.aber.dcs.cs22120.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs22120.group16.main.recordpackage.Record.DAFORLEVEL;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
 * @see FragmentPhoto
 * 
 * This is basically the DAFOR scale screen, in here is pretty much all needed to handle the DAFOR scale screen.
 *
 */
public class FragmentDAFOR extends Fragment {
	
	private int id;
	private FragmentPhoto photoFragment;
	private Button daforNext;
	private Button daforBack;
	private Button DAFOR_D;
	private Button DAFOR_A;
	private Button DAFOR_F;
	private Button DAFOR_O;
	private Button DAFOR_R;
	private TextView display;
	private View view;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	
	/**
	 * @author Steven(Sta17)
	 * 
	 * creates an view/screen and adds all needed, listeners, buttons etc, basically a constructor.
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__dafor,
	            container, false);
		photoFragment = new FragmentPhoto();
		rec = new Record();
		display = (TextView) view.findViewById(R.id.DAFORdisplay);
		
		Bundle args = getArguments();
		if(args != null){
		if(args.containsKey("RECORD")){
			rec = (Record) args.getSerializable("RECORD");
			name = args.getString("NAME");
			phone = args.getString("PHONE");
			email = args.getString("EMAIL");
			id = args.getInt("ID");
			rec.getDAFOR();
			DAFORLEVEL daf = rec.getDAFOR();
			if(daf != null){
				String input = daf.name();
				display.append(input);
			}
		}}

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
		
		daforNext = (Button) view.findViewById(R.id.daforNext);
        daforNext.setOnClickListener(new OnClickListener() {
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
				photoFragment.setArguments(args);
				transaction.replace(id, photoFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			} });
        
        daforBack = (Button) view.findViewById(R.id.daforBack);
        daforBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getFragmentManager().popBackStack();
			} });
        
        DAFOR_D = (Button) view.findViewById(R.id.DAFOR_D);
        DAFOR_D.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				rec.setDAFOR(Record.DAFORLEVEL.Dominant);
				display.setText("DAFOR Level: Dominant");
			} });
        DAFOR_A = (Button) view.findViewById(R.id.DAFOR_A);
        DAFOR_A.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				rec.setDAFOR(Record.DAFORLEVEL.Abundant);
				display.setText("DAFOR Level: Abundant");
			} });
        DAFOR_F = (Button) view.findViewById(R.id.DAFOR_F);
        DAFOR_F.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				rec.setDAFOR(Record.DAFORLEVEL.Frequent);
				display.setText("DAFOR Level: Frequent");
			} });
        DAFOR_O = (Button) view.findViewById(R.id.DAFOR_O);
        DAFOR_O.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				rec.setDAFOR(Record.DAFORLEVEL.Occasional);
				display.setText("DAFOR Level: Occasional");
			} });
        DAFOR_R = (Button) view.findViewById(R.id.DAFOR_R);
        DAFOR_R.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				rec.setDAFOR(Record.DAFORLEVEL.Rare);
				display.setText("DAFOR Level: Rare");
			} });
	}
}