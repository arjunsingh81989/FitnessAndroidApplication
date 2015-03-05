/*******************************************************************************************************************************************
  
   Author: Arjun Singh

   Group ID: 02

   Note For The TA : 	Please checkout my work from my branch : Arjun 

						When you run my project please use the following UserName and PassWord to Log-In. You do not need to register an account
						as I have already registered your username and password in "users" table of our database"db30902". But you can view the Registration
						page by clicking on "New To Account? Register Here" clickable textview present on the Login Screen. Also, on the
						Login and Registration screens you can try out different test cases like -- Logging in or registering with no username or
						password, typing in different strings in Password and Confirm Password TextViews of Registration Screen, registering with
						a UserName that has already been taken by some other user, logging in with a username that has not been registered in the "users" table
					    of the database db30902.   
						
						Your archive records have also been added to the "archive" table of "db30902". 
		   				
		   				UserName = Lingjian
		   				PassWord = Meng
		   				
		   				Once you Log-In you will see the HomeScreen with four Buttons: Go on a run,  My Archive, My Friends and Upgrade.
		   				On the HomeScreen please click on My Archive button. This will load up the Archive page which is divided into 
		   				four columns : Date, Time, Distance and Calories. Here you can test the Sort functionality by clicking on any of these
		   				four columns. For example: To sort the entries in the date column in the descending order, click on the Text "Date" -- You will
		   				see an arrow_down image and the newest date will show up at the top of this column. If you click the text Date again then
		   				you will see an arrow_up image and the oldest date will show up at the top of the column -- this style is ascending.
		   				Similarly, to sort other columns click on text Time, Distance or Calories. The entries in these columns can be sorted
		   				in ascending (arrow_up image) and descending (arrow_down image) order. The entries in the date column are in the format :yy:mm:dd
		   				The "yy" part will only show two numeric characters -- 06 , 07 , 08 , 09 , 13 etc for years 2006 , 2007 , 2008 , 2009 , 2013 respectively.
		   				
		   				On the HomeScreen you can then click on Upgrade to check how the information is stored in the upgrade table of
		   				db30902.
 

 ********************************************************************************************************************************************/

package com.demo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import com.demo.database.DatabaseOperations;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity implements OnClickListener
{

	// This just like a drop down menu -- combo box -- and has two values --
	// Free and Premium
	private Spinner spinner;
	private ArrayList<String> listItems;
	private ArrayAdapter<String> adapter;
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		/*
		 * ------- Reference :
		 * http://developer.android.com/guide/topics/ui/controls/spinner.html --
		 */
		// This is the standard way of creating a spinner object. I have give
		// credit above.

		// Get the spinner object
		spinner = (Spinner) findViewById(R.id.spinner);
		// Define an arraylist to store string values -- Free and Premium
		listItems = new ArrayList<String>();
		listItems.add("Free");
		listItems.add("Premium");
		adapter = new ArrayAdapter<String>(Registration.this,
				R.layout.spinner_layout, listItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		/*
		 * ----------------------------------------------------------------------
		 * ---------------------
		 */

	}

	// When a view ( which has an onClickListener set) is clicked, get the id of
	// that view from the resource folder and
	// use the Intent to navigate from one activity to another.
	// I have not setOnClickListerner on link_to_login and btnRegister button in
	// this java file, instead I have added
	// android:onClick="onClick" in register.xml file under Register Button and
	// link_to_login textview.
	public void onClick(View view)
	{
		// Get the id of the view and use it determine the next screen.
		switch (view.getId())
		{
		case R.id.link_to_login:
			// Here the next screen will be Login Screen since the user has
			// clicked the textview for link_to_login.
			Intent intent = new Intent(Registration.this, Login.class);
			// Now, start the Login Screen
			startActivity(intent);
			break;
		case R.id.btnRegister:
			// Call the register method if the user clicks the Register button.
			// This will take us to the homescreen screen after sending data to
			// the server.
			try
			{
				Resgister1();
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
		default:
			break;
		}
	}

	private class Register1 extends AsyncTask<String, Void, String>
	{
	
		@Override
		protected String doInBackground(String... params) {
			try {
				register();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	
	
		// This method will send information about the user -- like username,
		// password and usertype
		// to the server. After successfull registration of the user, the user will
		// move to the HomeScreen.
		private void register() throws Exception
		{

			// Get the username entered by the user in EditText with id txtAccount
			String user = ((EditText) findViewById(R.id.txtAccount)).getText()
					.toString();

			// Get the password entered by the user in EditText with id txtPassword
			String pass = ((EditText) findViewById(R.id.txtPassword)).getText()
					.toString();

			// Get the confirm password string entered by the user in EditText with
			// id confirmPwd
			String passConfirm = ((EditText) findViewById(R.id.confirmPwd))
					.getText().toString();

			// if the new user forgets to enter his username
			if (user.equals(""))
			{
				
				handler.post( new Runnable(){
					   public void run(){
						   Toast.makeText(getApplicationContext(), "Please enter your UserName", 3).show();
					       }
					});
				
				
				
				//Toast.makeText(getApplicationContext(), "Please enter your UserName", 3).show();
			}

			// If the new user forgets to enter his password
			else if (pass.equals(""))
			{
				Toast.makeText(getApplicationContext(), "Please enter your Password", 3).show();
			}

			// If the new user forgets to conform his password.
			else if (passConfirm.equals(""))
			{
				Toast.makeText(getApplicationContext(), "Please confirm your PassWord", 3).show();
			}

			// If the new user's password and confirm password string do not match.
			else if (!pass.equals(passConfirm))
			{
				Toast.makeText(getApplicationContext(), "Password does not match", 3).show();
			}

			// checkAccount will call JSON_Obj which will send the username (of the
			// person trying
			// to register) to the server where the PHP file will check if same
			// username has
			// already been registered or not.
			else if (DatabaseOperations.checkUserExists(user))
			{
				Toast.makeText(
						getApplicationContext(),
						user
								+ " has already been taken. Choose a different UserName",
						5).show();
			}

			else
			{
				// type will store what kind of subscription the user wants to
				// enroll in
				String type = "";

				// if the user selects free from the drop down menu, then type will
				// be Free, else it will be Premium
				if (spinner.getSelectedItem().equals("Free"))
				{
					type = "Free";
				} else
				{
					type = "Premium";
				}

				// addUser will call JSON_Obj that will use HttpPost to send
				// NameValuePairs for username, password
				// and type to the server.This info will be inserted in the users
				// table by the PHP file on the
				// server.
				
			
				
				
				DatabaseOperations.putUser(user, pass, type);
				//int id = DatabaseOperations.getUserID(user);
				DatabaseOperations.UserInfoInUserSettingsTableAdded(user);

				// After successful registration, the user will move to the
				// HomeScreen.
			//	Intent intent = new Intent(Registration.this, HomeScreen.class);

				// Start the HomeScreen activity.
				//startActivity(intent);
				// And end the Registration Activity.
				handler.post( new Runnable(){
					   public void run(){
						   Toast.makeText(getApplicationContext(), "You are a Registered User!                          Please Login Here ", 8).show();
					       }
					});
				
				
				Registration.this.finish();
					//this.finish();
			}
			
		}

	
	}
	public void Resgister1() {
		Register1 task = new Register1();
	      task.execute();

	    }
}
