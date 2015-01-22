package com.example.the_botonists_companion;

import recordPackage.Record;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView nametarget;
	TextView phonetarget;
	TextView emailtarget;
	TextView commenttarget;
	TextView latitudetarget;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
		createButtons();
        
		modifyFields();
        	            
	}

	public void modifyFields(){
		//taking the textview1 reference
        nametarget = (TextView)findViewById(R.id.nametargetfield);
        phonetarget = (TextView)findViewById(R.id.phonetargetfield);
        emailtarget = (TextView)findViewById(R.id.emailtargetfield);
        commenttarget = (TextView)findViewById(R.id.commenttargetfield);
        latitudetarget = (TextView)findViewById(R.id.latitudetargetfield);
       
        
        Bundle bunble=getIntent().getExtras();
        	        if(bunble!=null){
        	            //Getting the value stored in the name "NAME"
        	            String user_name=bunble.getString("NAME");
        	            String user_email=bunble.getString("EMAIL");
        	            String user_phone=bunble.getString("PHONE");
        	            Record rec = (Record) bunble.getSerializable("RECORD");
        	            //appending the value to the contents of textView1.
        	            
        	            nametarget.append(" "+user_name);
        	            phonetarget.append(" "+user_phone);
        	            emailtarget.append(" "+user_email);
        	            latitudetarget.append(" "+rec.getLatitude());
        	            commenttarget.append(" "+rec.getComment());
        	        }
        	            
	}
	
	public void createButtons(){
		Button userdetails = (Button) findViewById(R.id.userdetails);
        userdetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), UserDetails.class);
                startActivityForResult(myIntent, 0);
            }

        });
		
        Button next = (Button) findViewById(R.id.toCamera);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Camera.class);
                startActivityForResult(myIntent, 0);
            }

        });
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
