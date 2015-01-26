package uk.ac.aber.dcs.cs22120.group16.fragments;

import java.util.ArrayList;

import uk.ac.aber.dcs.cs22120.group16.plantpackage.Plant;
import uk.ac.aber.dcs.cs22120.group16.plantpackage.PlantAdapter;
import uk.ac.aber.dcs.cs22120.group16.recordpackage.Record;

import com.example.botanistcompanionapp.R;
import com.example.botanistcompanionapp.R.id;
import com.example.botanistcompanionapp.R.layout;

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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
 
public class Fragment_Select_Plant extends Fragment {

	private Fragment DAFORFragment;
	private Button pNext;
	private Button pBack;
	private Record rec;
	private String email;
	private String name;
	private String phone;
	private PlantListCommunicator mCallback;
	private ArrayList<Plant> plantlist;
	private int id;
	private PlantAdapter listadapter;
	private ListView plantListView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment__select__plant,
	            container, false);
		DAFORFragment = new Fragment_DAFOR();
		rec = new Record();
		plantlist = mCallback.getPlantlist();
		Bundle args0 = getArguments();
		if(args0 != null){
		if(args0.containsKey("RECORD")){
			rec = (Record) args0.getSerializable("RECORD");
			name = args0.getString("NAME");
			phone = args0.getString("PHONE");
			email = args0.getString("EMAIL");
			id = args0.getInt("ID");
		}}
		
		pNext = (Button) view.findViewById(R.id.PSNext);
		pNext.setOnClickListener(new OnClickListener() {
			
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
				DAFORFragment.setArguments(args);
				transaction.replace(id, DAFORFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
			
		});
		
		pBack = (Button) view.findViewById(R.id.PSBack);
		pBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getFragmentManager().popBackStack();
			} });
		
		//mainListView = (ListView) findViewById( R.id.PSlistView );
		
		//View view = inflater.inflate(R.layout.fragment_plant_selection, container, false);
				plantListView = (ListView) view.findViewById( R.id.PSlistView ); 
				
				//add items to list rows
				
			    //add each row (item) to list
			    listadapter = new PlantAdapter(getActivity(), plantlist);
			    
			    plantListView.setAdapter(listadapter);
			    
			    //set what happens when an item in the list is clicked. A variable "plant" is given the text (name) 
			    //of the plant (listItem)
			    plantListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							rec.setPlantCommon(plantlist.get((int) id).getCommon());
							rec.setPlantLatin(plantlist.get((int) id).getLatin());
					}
		        });
		
		return view;
	}

	// Container Activity must implement this interface
    public interface PlantListCommunicator {
        public ArrayList<Plant> getPlantlist();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (PlantListCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PlantListCommunicator");
        }
    }
}