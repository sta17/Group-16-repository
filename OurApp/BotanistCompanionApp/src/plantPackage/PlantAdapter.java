package plantPackage;

import java.util.ArrayList;
import java.util.List;

import com.example.botanistcompanionapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlantAdapter extends ArrayAdapter<Plant> {

	private Activity activity;
    private ArrayList<Plant> plantlist;
    private final Context context;
 
    public PlantAdapter(Context context, List<Plant> itemsArrayList) {
		
		super(context, R.layout.plantrow, itemsArrayList);
		 
        this.context = context;
        this.plantlist = (ArrayList<Plant>) itemsArrayList;
	}

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