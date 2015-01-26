package uk.ac.aber.dcs.cs22120.group16.fragments;

import uk.ac.aber.dcs.cs22120.group16.recordpackage.Record;

import com.example.botanistcompanionapp.R;
import com.example.botanistcompanionapp.R.id;
import com.example.botanistcompanionapp.R.layout;

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
	
	private Fragment photoFragment;
	private Button CNext;
	private Button CBack;
	private EditText commentfield;
	private View view;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	private int id;
	
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
			id = args.getInt("ID");
			
			if(rec.getComment() != null){
				commentfield.append(rec.getComment());
			}
			
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
				args.putInt("ID", id);
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				photoFragment.setArguments(args);
				transaction.replace(id, photoFragment);
				transaction.addToBackStack(null);
				transaction.commit();	
			} });
		
		CBack = (Button) view.findViewById(R.id.CBack);
		CBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String comment = commentfield.getText().toString();
				rec.setComment(comment);
				getFragmentManager().popBackStack();
			} });
		return view;
	}

}