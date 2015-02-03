package uk.ac.aber.dcs.cs221.group16.main.fragments;

import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.RecordAdapter;
import uk.ac.aber.dcs.cs22120.group16.main.R;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

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
 * @see FragmentUserInput
 * 
 * This is the Select record screen, in here is pretty much all needed to handle the select record screen.
 *
 */
public class FragmentRecordSelection extends Fragment {

	private FragmentUserInput fragment_userdata;
	private View view;
	private String email;
	private String name;
	private String phone;
	private Record rec;
	private PickRecordCommunicator mCallback;
	private List<Record> recordlist;
	private int id;
	private RecordAdapter listadapter;
	private ListView recordListView;

	/**
	 * Creates a view populated by the fragment_record_selection xml file and adds
	 * buttons, data and functionality to this view.
	 * @param inflater 
	 * @param container
	 * @param saved instance
	 * @return view
	 * 
	 * @see Record
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment__record_selection, container,
				false);

		fragment_userdata = new FragmentUserInput();
		rec = null;
		recordlist = mCallback.getRecordlist();
		Bundle args = getArguments();
		name = args.getString("NAME");
		phone = args.getString("PHONE");
		email = args.getString("EMAIL");
		id = args.getInt("ID");

		recordListView = (ListView) view.findViewById(R.id.RSlistView);

		// add each row (item) to list
		listadapter = new RecordAdapter(getActivity(), recordlist);
		recordListView.setAdapter(listadapter);

		// set what happens when an item in the list is clicked. A variable
		// "record" is given the text (name)
		// of the plant (listItem)
		recordListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				rec = recordlist.get((int) id);
				selectRecord(rec);
			}
		});

		return view;
	}
	
	/**
	 * 
	 * This contains options for which the user can manipulate a record from;
	 * 
	 * @param temp the record which the user has selected to manipulate
	 */
	private void selectRecord(final Record temp) {
		final CharSequence[] items = { "Edit Record", "Delete Record", "Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Choose Action");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			/**
			 * 
			 * Goes to next page(userdata), stores values as well as putting this fragment on top of
			 * the stack for later popback functionality if Edit Record selected
			 * 
			 * if Delete Record is selected then the delete record method is run
			 * 
			 * otherwise dismiss dialog window.
			 * 
			 */
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Edit Record")) {
					//mCallback.saveOriginal(temp, item);
					
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

	/**
	 * This method is for deleting a record from the list.
	 * 
	 * @param temp the record to be deleted;
	 */
	private void DeleteRecord(final Record temp) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
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
		builder.setMessage("Are you sure you want to delete the record?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}

	private void setRecNull() {
		this.rec = null;
	}

	/**
	 *
	 *Container Activity must implement this interface. 
	 *This interface makes sure that the activity class 
	 *has the methods needed to get the record list from the database
	 *and return them to the fragment which is to make a display list 
	 *out of it.
	 *
	 */
	public interface PickRecordCommunicator {
		public List<Record> getRecordlist();
		public void saveOriginal(Record rec, int index);
		public void wipeOriginal();
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
			mCallback = (PickRecordCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement PickRecordCommunicator");
		}
	}
}