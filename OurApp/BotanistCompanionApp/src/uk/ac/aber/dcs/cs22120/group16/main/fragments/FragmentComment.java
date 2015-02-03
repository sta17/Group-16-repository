package uk.ac.aber.dcs.cs22120.group16.main.fragments;

import uk.ac.aber.dcs.cs22120.group16.main.R;
import uk.ac.aber.dcs.cs22120.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs22120.group16.util.Validator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17),
 * @author Dan(Daf16)
 * @since 1.5
 * @version 2.2
 * @see NewRecord
 * @see EditRecord
 * 
 * This is basically the phone screen, in here is pretty much all needed to handle the phone screen.
 *
 */
public class FragmentComment extends Fragment {
	
	private Button CDone;
	private Button CBack;
	private EditText commentfield;
	private View view;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	private RecordCommunicator mCallback;
	
	/**
	 * @author Steven(Sta17)
	 * 
	 * creates an view/screen and adds all needed, listeners, buttons etc, basically a constructor.
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__comment, container, false);
		commentfield = (EditText) view.findViewById(R.id.commentfield);
		rec = new Record();
		
		Bundle args = getArguments();
		if(args != null){
		if(args.containsKey("RECORD")){
			rec = (Record) args.getSerializable("RECORD");
			name = args.getString("NAME");
			phone = args.getString("PHONE");
			email = args.getString("EMAIL");
			
			if(rec.getComment() != null){
				commentfield.append(rec.getComment());
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
	private void SetupButtons() {
		
		CDone = (Button) view.findViewById(R.id.CDone);
		CDone.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				Validator tester = new Validator();
				if(tester.isCommentCorrect(name)){
					String comment = commentfield.getText().toString();
					rec.setComment(comment);
					mCallback.ReturnFinalRecord(rec, email, name, phone);
				} else{
					Toast.makeText(getActivity().getApplicationContext(),
							"Comment field contains bad input",
							Toast.LENGTH_SHORT).show();
				}
			} });
		
		CBack = (Button) view.findViewById(R.id.CBack);
		CBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String comment = commentfield.getText().toString();
				rec.setComment(comment);
				getFragmentManager().popBackStack();
			} });
	}
	
	    /**
	     * @author Steven(Sta17)
	     * 
	     * Container Activity must implement this interface. This interface is 
	     * basically just to make sure, that the final fragment, this one can 
	     * communicate the end results to its activity on completion. 
	     *
	     */
		public interface RecordCommunicator {
			public void ReturnFinalRecord(Record temp, String Email, String Name,
					String Phone);
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			// This makes sure that the container activity has implemented
			// the callback interface. If not, it throws an exception
			try {
				mCallback = (RecordCommunicator) activity;
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString()
						+ " must implement NewRecordCommunicator");
			}
		}

}