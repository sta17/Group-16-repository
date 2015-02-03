package uk.ac.aber.dcs.cs22120.group16.main.fragments;

import uk.ac.aber.dcs.cs22120.group16.main.R;
import uk.ac.aber.dcs.cs22120.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs22120.group16.util.Validator;
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
 * @since 1.2
 * @version 2.2
 * @see NewRecord
 * @see EditRecord
 * @see FragmentSelectPlant
 * 
 * This is basically the user data input screen, in here is pretty much
 * all needed to handle the user data input screen screen.
 *
 */
public class FragmentUserInput extends Fragment {

	private FragmentSelectPlant Select_Plant;
	private Button UNext;
	private Button UBack;
	private EditText emailfield;
	private EditText namefield;
	private EditText phonefield;
	private String email;
	private String name;
	private String phone;
	private Record rec;
	private View view;
	private int id;

	/**
	 * @author Steven(Sta17)
	 * 
	 *         creates an view/screen and adds all needed, listeners, buttons
	 *         etc, basically a constructor.
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__userinput, container, false);
		Select_Plant = new FragmentSelectPlant();
		emailfield = (EditText) view.findViewById(R.id.edit_email);
		namefield = (EditText) view.findViewById(R.id.edit_name);
		phonefield = (EditText) view.findViewById(R.id.edit_phone);

		rec = new Record();
		Bundle args = getArguments();
		if (args != null) {
			if (args.containsKey("RECORD")) {
				rec = (Record) args.getSerializable("RECORD");
				name = args.getString("NAME");
				phone = args.getString("PHONE");
				email = args.getString("EMAIL");
				id = args.getInt("ID");
			}
		}

		// if the details already exit 
		// add them to the fields.
		if (email != null) {
			emailfield.append(email);
		}
		if (phone != null) {
			phonefield.append(phone);
		}
		if (name != null) {
			namefield.append(name);
		}

		SetupButtons();
		return view;
	}

	/**
	 * 
	 * @author Steven(Sta17)
	 * 
	 *         basically sets up the buttons on the page, are on their own for
	 *         reason of code modulisation and ease of view.
	 * 
	 */
	private void SetupButtons() {
		UNext = (Button) view.findViewById(R.id.UNext);
		UNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				name = namefield.getText().toString();
				phone = phonefield.getText().toString();
				email = emailfield.getText().toString();

				Validator tester = new Validator();
				if (tester.validate(email)) {
					if (tester.isPhoneNumberCorrect(phone)) {
						if (tester.isNameCorrect(name)) {
							Bundle args = new Bundle();
							args.putSerializable("RECORD", rec);
							args.putString("NAME", name);
							args.putString("EMAIL", email);
							args.putString("PHONE", phone);
							args.putInt("ID", id);
							FragmentManager fm = getFragmentManager();
							FragmentTransaction transaction = fm
									.beginTransaction();
							Select_Plant.setArguments(args);
							transaction.replace(id, Select_Plant);
							transaction.addToBackStack(null);
							transaction.commit();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Name is not in proper format",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity().getApplicationContext(),
								"Phone is not in proper format",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"Email is not in proper format", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		UBack = (Button) view.findViewById(R.id.UBack);
		UBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (id == R.id.fragment_container) {
					getActivity().finish();
				} else {
					getFragmentManager().popBackStack();
				}
			}
		});
	}
}