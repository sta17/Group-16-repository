package com.example.the_botonists_companion;

import recordPackage.Record;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserDetails extends Activity {

	EditText edit_name;
	EditText edit_email;
	EditText edit_phone;
	Button btn_ok;
	String name;
	String email;
	String phone;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		
		edit_name = (EditText)findViewById(R.id.nameinput);
		edit_phone = (EditText)findViewById(R.id.phoneinput);
		edit_email = (EditText)findViewById(R.id.emailinput);
		
		ExtractData();
	}

	public void ExtractData(){
		btn_ok=(Button)findViewById(R.id.userdetails);
		
		//"Ok" Button listener
		btn_ok.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
			
		name=edit_name.getText().toString();
		phone=edit_phone.getText().toString();
		email=edit_email.getText().toString();
		
			                System.out.println("name:"+name);
			 
			                //Checking whether the EditText is empty or not
			                if((name.equals(""))==false && (email.equals(""))==false && (phone.equals(""))==false){
			 
			            		Record rec = new Record();
			            		rec.setComment("bla bla");
			            		rec.setLatitude(22);
			                	
			                    //Creating a new intent
			                    Intent intent=new Intent(UserDetails.this, MainActivity.class);
			 
			                    //Sending data to next activity using putExtra method
			                    intent.putExtra("NAME", name);
			                    intent.putExtra("EMAIL", email);
			                    intent.putExtra("PHONE", phone);
			                    intent.putExtra("RECORD", rec);
			 
			                    //starting new activity
			                    //startActivity(intent);
			                    startService(intent);
			                    
			                    /*
			                    SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
			                    SharedPreferences.Editor edit = prefs.edit();
			                    edit.putString("MID", mailID );
			                    edit.put
			                    edit.commit();
			                    */
			                    
			                }
			                else{
			                    Toast.makeText(UserDetails.this, "Please Enter your name", 1000).show();
			                }
								}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_details, menu);
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
