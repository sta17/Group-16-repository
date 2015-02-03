package uk.ac.aber.dcs.cs221.group16.main.recordpackage;

import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record.DAFORLEVEL;
import uk.ac.aber.dcs.cs22120.group16.main.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @see Record
 * 
 * This is for adapting a arraylist of record into a viewable and clickable object on the screen.
 */
public class RecordAdapter extends ArrayAdapter<Record> {

	private static String newline = System.getProperty("line.separator");
    private ArrayList<Record> recordlist;
    private final Context context;
 
    /**
     * constructor
     * 
     * @param context the of the class housing the activity.
     * @param itemsArrayList the list from which a record to be made into a row is aquired
     */
    public RecordAdapter(Context context, List<Record> itemsArrayList) {
		
		super(context, R.layout.recordrow, itemsArrayList);
		 
        this.context = context;
        this.recordlist = (ArrayList<Record>) itemsArrayList;
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
        View rowView = inflater.inflate(R.layout.recordrow, parent, false);

        // 3. Get the two text view from the rowView
        TextView latin = (TextView) rowView.findViewById(R.id.latinname);
        TextView dateview = (TextView) rowView.findViewById(R.id.datename);
        TextView daforscale = (TextView) rowView.findViewById(R.id.daforscale);

        // 4. Set the text for textView
        latin.setText(recordlist.get(position).getPlantLatin());
        
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        String date = recordlist.get(position).getEditdate();
        //SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        //String stringDate = sdf.format(date);
        //dateview.setText(stringDate);
        dateview.setText(date);
        
        DAFORLEVEL dafor = recordlist.get(position).getDAFOR();
        daforscale.setText("DAFOR: ");
        if(dafor == Record.DAFORLEVEL.Dominant){
        	daforscale.append(newline+"     D");
        } else if(dafor == Record.DAFORLEVEL.Abundant){
        	daforscale.append(newline+"     A");
        } else if(dafor == Record.DAFORLEVEL.Frequent){
        	daforscale.append(newline+"     F");
        } else if(dafor == Record.DAFORLEVEL.Occasional){
        	daforscale.append(newline+"     O");
        } else{
        	daforscale.append(newline+"     R");
        }
        
        // 5. retrn rowView
        return rowView;
    } 
}