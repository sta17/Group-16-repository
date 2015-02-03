package uk.ac.aber.dcs.cs221.group16.main.plantpackage;

import uk.ac.aber.dcs.cs22120.group16.main.R;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17)
 * @since 1.9
 * @version 2.0
 * 
 *This is for adapting a arraylist of plants into a viewable and clickable object on the screen.
 *
 */
public class PlantAdapter extends ArrayAdapter<Plant> {

    private ArrayList<Plant> plantlist;
    private final Context context;
 
    /**
     * Constructor
     * 
     * @param context the context.
     * @param itemsArrayList the arraylist from which to base the display on.
     */
    public PlantAdapter(Context context, List<Plant> itemsArrayList) {
		
		super(context, R.layout.plantrow, itemsArrayList);
		 
        this.context = context;
        this.plantlist = (ArrayList<Plant>) itemsArrayList;
	}

    /**
     * creates a single row item and returns it.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.plantrow, parent, false);

        // 3. Get the two text view from the rowView
        TextView common = (TextView) rowView.findViewById(R.id.plantRowTextViewcommon);
        TextView latin = (TextView) rowView.findViewById(R.id.plantRowTextViewlatin);

        // 4. Set the text for textView
        common.setText(plantlist.get(position).getCommon());
        latin.setText(plantlist.get(position).getLatin());

        // 5. retrn rowView
        return rowView;
    }
    
}