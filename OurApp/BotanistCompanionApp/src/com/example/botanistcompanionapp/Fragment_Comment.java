package com.example.botanistcompanionapp;

import recordPackage.Record;
import recordPackage.Record.DAFORLEVEL;
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
import android.widget.TextView;

public class Fragment_Comment extends Fragment {
	final static String ARG_POSITION = "position";
	Fragment photoFragment;
	Button CNext;
	Button CBack;
	EditText commentfield;
	private View view;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__comment, container, false);
		commentfield = (EditText) view.findViewById(R.id.commentfield);
		photoFragment = new Fragment_Photo();
		rec = new Record();
		
		Bundle args = getArguments();
		if(args != null){
		if(args.containsKey("RECORD")){
			rec = (Record) args.getSerializable("RECORD");
			name = args.getString("NAME");
			phone = args.getString("PHONE");
			email = args.getString("EMAIL");
		}}
		
		CNext = (Button) view.findViewById(R.id.CNext);
		CNext.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				String comment = commentfield.getText().toString();
				rec.setComment(comment);
				Bundle args = new Bundle();
				args.putSerializable("RECORD", rec);
				args.putString("NAME", name);
				args.putString("EMAIL", email);
				args.putString("PHONE", phone);
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				photoFragment.setArguments(args);
				transaction.replace(R.id.fragment_container, photoFragment);
				transaction.addToBackStack(null);
				transaction.commit();	
			} });
		
		CBack = (Button) view.findViewById(R.id.CBack);
		CBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getFragmentManager().popBackStack();
			} });
		return view;
	}

}