package com.demo.activity;

import java.util.ArrayList;
import java.util.Locale;

import com.demo.database.DatabaseOperations;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class OptionActivity extends Activity implements OnClickListener {
	private LinearLayout layoutAppPre, subLayout, layoutProfile, subProfile,
			layoutAccount, subAccount;
	private ImageView img1, img2, img3;
	boolean isButton = true;
	boolean isProfile = true;
	boolean isAccount = true;
	Handler handler = new Handler();
	private Spinner genderSpinner;
	private ArrayList<String> listItems;
	private ArrayAdapter<String> genderAdapter;
	
	private ArrayList<String> languages;
	private ArrayAdapter<String> adapter;
	private Spinner spinner;
	private Locale current;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_activity);
		//img1 = (ImageView) findViewById(R.id.img1);
		img2 = (ImageView) findViewById(R.id.img2);
		img3 = (ImageView) findViewById(R.id.img3);
		spinner = (Spinner)findViewById(R.id.spinner);
		
		//layoutAppPre = (LinearLayout) findViewById(R.id.layout_appre);
		//subLayout = (LinearLayout) findViewById(R.id.subAppPre);
		layoutProfile = (LinearLayout) findViewById(R.id.layout_profile);
		subProfile = (LinearLayout) findViewById(R.id.subProfile);
		layoutAccount = (LinearLayout) findViewById(R.id.layout_account);
		subAccount = (LinearLayout) findViewById(R.id.subAccount);
		//subLayout.setVisibility(View.GONE);
		subProfile.setVisibility(View.GONE);
		subAccount.setVisibility(View.GONE);
	//	layoutAppPre.setOnClickListener(this);
		layoutProfile.setOnClickListener(this);
		layoutAccount.setOnClickListener(this);
		 Button saveButton = (Button) findViewById(R.id.saveButton);    
		 saveButton .setOnClickListener(this);
		 Button changePasswordButton = (Button) findViewById(R.id.submit);    
		 changePasswordButton .setOnClickListener(this);
		 
		 Button deleteButton = (Button) findViewById(R.id.deleteButton);    
		 deleteButton .setOnClickListener(this);
		 
		 
/*
			final String userName = getIntent().getExtras().getString(
					Login.USER_NAME_KEY);
		 try {
			//Toast.makeText(getApplicationContext(), userName,5).show();//DatabaseOperations.getUserID(userName),5).show();
				Toast.makeText(this, DatabaseOperations.getUserID(userName),Toast.LENGTH_SHORT).show();

		 } catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	String genderType = "";

		// if the user selects free from the drop down menu, then type will
		// be Free, else it will be Premium
		if (spinner.getSelectedItem().equals("Free"))
		{
			genderType = "Free";
		} else
		{
			genderType = "Premium";
		}

	
	
	/*
		<item>Male / Macho</item>
        <item>Female / Femenino</item>
	
	*/
	
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.backbtn:
			
			Intent intent = new Intent(this,
					HomeScreen.class);
			intent.putExtra(Login.USER_NAME_KEY, Login.user);
			startActivity(intent);
			
			/*Intent newIntent;
			newIntent = new Intent(this, HomeScreen.class);
			
			startActivity(newIntent);*/
			// Since, we have moved to a different screen, we can now close the
			// current activity.
			this.finish();
			break;
		
	
		
			
			case R.id.deleteButton:
			
				try
				{
					
					
					
					deleteAccount();
					v.setEnabled(false);
					 
					//Intent intent1 = new Intent(Registration.this, HomeScreen.class);

					// Start the HomeScreen activity.
					//startActivity(intent1);
					// And end the Registration Activity.
					//this.finish();
					//	this.finish();
					Intent intent1 = new Intent(OptionActivity.this, Registration.class);

					// Start the HomeScreen activity.
					startActivity(intent1);
					// And end the Registration Activity.
					this.finish();
					
					Toast.makeText(getApplicationContext(), "Your Account has been deleted ! You may register a new account", 16).show();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
		
			/*		
		case R.id.saveButton:
		{
			
			
			
			Toast.makeText(
					this,
					
					(String)spinner.getSelectedItem(),
					5).show();
		}
			
			*/
	
		
		
		case R.id.saveButton:
			// Call the register method if the user clicks the Register button.
			// This will take us to the homescreen screen after sending data to
			// the server.
			try
			{
				
				
				
				Save();
				v.setEnabled(false);
				  Toast.makeText(getApplicationContext(), "Your Profile has been Saved ! ", 8).show();
				//Intent intent1 = new Intent(Registration.this, HomeScreen.class);

				// Start the HomeScreen activity.
				//startActivity(intent1);
				// And end the Registration Activity.
				//this.finish();
				//	this.finish();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			
			
		case R.id.submit:
			// Call the register method if the user clicks the Register button.
			// This will take us to the homescreen screen after sending data to
			// the server.
			try
			{
				
				
				
				changePassword();
				v.setEnabled(false);
				
				Intent intent1 = new Intent(OptionActivity.this, Login.class);

				// Start the HomeScreen activity.
				startActivity(intent1);
				// And end the Registration Activity.
				this.finish();
				Toast.makeText(getApplicationContext(), "Your Password has been changed ! Please Login with your new password ", 16).show();
				//	this.finish();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
	/*	case R.id.layout_appre:
			if (isButton) {
				img1.setImageDrawable(getResources().getDrawable(R.drawable.up));
				subLayout.setVisibility(View.VISIBLE);
				isButton = false;
			} else {
				img1.setImageDrawable(getResources().getDrawable(
						R.drawable.down));
				subLayout.setVisibility(View.GONE);
				isButton = true;
			}

			break;*/

		case R.id.layout_profile:
			if (isProfile) {
				img2.setImageDrawable(getResources().getDrawable(R.drawable.up));
				subProfile.setVisibility(View.VISIBLE);
				isProfile = false;
			} else {
				img2.setImageDrawable(getResources().getDrawable(
						R.drawable.down));
				subProfile.setVisibility(View.GONE);
				isProfile = true;
			}
			break;
		case R.id.layout_account:
			if (isAccount) {
				img3.setImageDrawable(getResources().getDrawable(R.drawable.up));
				subAccount.setVisibility(View.VISIBLE);
				isAccount = false;
			} else {
				img3.setImageDrawable(getResources().getDrawable(
						R.drawable.down));
				subAccount.setVisibility(View.GONE);
				isAccount = true;
			}
		}
	}

	
	
	private class deleteAccount extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... arg0) {
		
			try {
				delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private void delete() throws Exception
		{
			final String userName = getIntent().getExtras().getString(
				Login.USER_NAME_KEY);
			
		//	((User) this.getApplication()).getUsername()
			
		
		/*	handler.post( new Runnable(){
				  
				public void run(){
					   try {
						Toast.makeText(getApplicationContext(), DatabaseOperations.getUserID(userName),5).show();//DatabaseOperations.getUserID(Login.USER_NAME_KEY), 3).show();
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				       }
				});*/
			//DatabaseOperations.UserInfoAdded(genderType, age, height, currentWeight, goalWeight, DatabaseOperations.getUserID(Login.USER_NAME_KEY));
			/*
			<item>Male / Macho</item>
	        <item>Female / Femenino</item>
		
		*/
			
		DatabaseOperations.deleteAccount(userName);
		
		}
	} // Inner Class
	
	public void deleteAccount() {
		deleteAccount task = new deleteAccount();
	      task.execute();

	    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// CHANGE
	
	private class changePassword extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... arg0) {
		
			try {
				changePW();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private void changePW() throws Exception
		{
			final String userName = getIntent().getExtras().getString(
				Login.USER_NAME_KEY);
			String password = ((EditText) findViewById(R.id.cnPassword)).getText()
					.toString();
		
		
		//	((User) this.getApplication()).getUsername()
			
		/*	handler.post( new Runnable(){
				  
				public void run(){
					   try {
						Toast.makeText(getApplicationContext(), DatabaseOperations.getUserID(userName),5).show();//DatabaseOperations.getUserID(Login.USER_NAME_KEY), 3).show();
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				       }
				});*/
			//DatabaseOperations.UserInfoAdded(genderType, age, height, currentWeight, goalWeight, DatabaseOperations.getUserID(Login.USER_NAME_KEY));
			/*
			<item>Male / Macho</item>
	        <item>Female / Femenino</item>
		
		*/
			
		DatabaseOperations.changeUserPassword(userName, password);
		
		}
	} // Inner Class
	
	public void changePassword () {
		changePassword task = new changePassword();
	      task.execute();

	    }
	
	// SAVE
	
	private class Save extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... arg0) {
		
			try {
				saveInfo();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private void saveInfo() throws Exception
		{
			final String userName = getIntent().getExtras().getString(
				Login.USER_NAME_KEY);
			String age = ((EditText) findViewById(R.id.age)).getText()
					.toString();
			String height = ((EditText) findViewById(R.id.height)).getText()
					.toString();
			String currentWeight = ((EditText) findViewById(R.id.currentWeight)).getText()
					.toString();
			String goalWeight = ((EditText) findViewById(R.id.goalWeight)).getText()
					.toString();
		
		//	((User) this.getApplication()).getUsername()
			
			String genderType = "";

			// if the user selects free from the drop down menu, then type will
			// be Free, else it will be Premium
			if (spinner.getSelectedItem().equals("Male / Macho"))
			{
				genderType = "Male";
			} else
			{
				genderType = "Female";
			}
		/*	handler.post( new Runnable(){
				  
				public void run(){
					   try {
						Toast.makeText(getApplicationContext(), DatabaseOperations.getUserID(userName),5).show();//DatabaseOperations.getUserID(Login.USER_NAME_KEY), 3).show();
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				       }
				});*/
			//DatabaseOperations.UserInfoAdded(genderType, age, height, currentWeight, goalWeight, DatabaseOperations.getUserID(Login.USER_NAME_KEY));
			/*
			<item>Male / Macho</item>
	        <item>Female / Femenino</item>
		
		*/
			
		DatabaseOperations.Add(age,genderType, currentWeight, goalWeight, height ,userName );
		
		}
	} // Inner Class
	
	public void Save() {
		Save task = new Save();
	      task.execute();

	    }

}
