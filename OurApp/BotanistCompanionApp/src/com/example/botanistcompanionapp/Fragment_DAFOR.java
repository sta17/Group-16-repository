package com.example.botanistcompanionapp;

import recordPackage.Record;
import recordPackage.Record.DAFORLEVEL;
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

public class Fragment_DAFOR extends Fragment {
	
	private int id;
	private Fragment commentFragment;
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
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__dafor,
	            container, false);
		commentFragment = new Fragment_Comment();
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
				commentFragment.setArguments(args);
				transaction.replace(id, commentFragment);
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
        
		return view;
	}
	
}