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
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.demo.database.DatabaseOperations;

// When the project is run then the app will first show the screen associated with the Login activity.
// I have added :   android:name="com.demo.activity.Login" in the first activity in the AndroidManifest.xml file.
public class Login extends Activity implements OnClickListener{

	// Creating a label for the username which the user enters which he tries
	// log into the account. This label will help us to retrieve the string value
	// associated with this label, which will be used to display the Welcome message
	// on the HomeScreen.
	public static final String USER_NAME_KEY= "userName";
	public static String user; 
	Handler handler = new Handler();
	private ArrayList<String> languages;
	private ArrayAdapter<String> adapter;
	private Spinner spinner;
	private Locale current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set the view to Login screen given in the res folder
		setContentView(R.layout.login);	
		spinner = (Spinner)findViewById(R.id.spinner);
		current = getResources().getConfiguration().locale;
		if(current.getLanguage().equals("en")){
			spinner.setSelection(0);
		}else if(current.getLanguage().equals("es")){
			spinner.setSelection(1);
		}else if(current.getLanguage().equals("ja")){
			spinner.setSelection(2);
		}
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
		    	int iCurrentSelection = spinner.getSelectedItemPosition();
		    	switch (iCurrentSelection) {
				case 0:
					if(!current.getLanguage().equals("en")){
						System.out.println("change language");
						setLocale("en");
					}
					break;
				case 1:
					if(!current.getLanguage().equals("es")){
						setLocale("es");
						System.out.println("change language");
					}
					break;
				case 2:
					if(!current.getLanguage().equals("ja")){
						setLocale("ja");
						System.out.println("change language");
					}
					break;
				default:
					break;
				}
		    } 

		    public void onNothingSelected(AdapterView<?> adapterView) {
		        return;
		    } 
		}); 
	}
	
	public void setLocale(String lang) { 
		Locale myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		Intent refresh = new Intent(getIntent());
		finish();
		startActivity(refresh);
	}

	// When a view ( which has an onClickListener set) is clicked, get the id of that view from the resource folder and
	// use the Intent to navigate from one activity to another.
	// I have not setOnClickListerner on the Login button in this java file, instead I havea added
	//  android:onClick="onClick" in login.xml file under Button. Note the login button is the only
	// view in this activity which has an OnClickListener set on it.
	public void onClick(View view) {

		// Get the id of the view from R.java file
		switch (view.getId()) {
		
		// If New To Account is clicked, create an intent object which allows to navigate from
		// from Login screen to Registration screen. link_to_register is a numerical id assocaited 
		// with the view labelled -- New To Account? Register Here.
		case R.id.link_to_register:
			
			
				Intent intent = new Intent(Login.this,
					Registration.class);
			// Since the user wants to register, we will start the Registration.java activity .
			startActivity(intent);
			break;
		
		// If the Login button is clicked, call the Login method which also creates an intent to go to 
		// the Home Screen -- this screen is not complete, but I will start working on it after I commit
		// this project	.
		case R.id.btnLogin:
			try
			{
				Login1();
				
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
	private class Login1 extends AsyncTask<String, Void, String>
	{
	
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				login();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		private void login() throws Exception {
		
		// Retrieve the username-- in the form of a String -- entered by the user in the EditText with 
		// id - txtUser.
		/*String*/ user = ((EditText) findViewById(R.id.txtUser)).getText()
				.toString();
		
		// Retrieve the password entered by the user in the EditText with the id - txtPwd
		String pass = ((EditText) findViewById(R.id.txtPwd)).getText()
				.toString();
		
		// If the user forgets to enter his username or password
		if(user.equals("") && pass.equals(""))
		{
			handler.post( new Runnable(){
				   public void run(){
					   Toast.makeText(getApplicationContext(), "Please enter your UserName and Password ! ", 8).show();
				       }
				});
			
		}
		
		// If he forgets his password
		else if(pass.equals(""))
		{
			handler.post( new Runnable(){
				   public void run(){
					   Toast.makeText(getApplicationContext(), "Please enter you Password ! ", 8).show();
				       }
				});
		}
		
		// And if he forgets his username
		else if(user.equals(""))
		{
			handler.post( new Runnable(){
				   public void run(){
					   Toast.makeText(getApplicationContext(), "Please enter your UserName ! ", 8).show();
				       }
				});
		}
		
		// If username and password have been entered, we should then check if the user is registered in our
		// users table of db30902.
		else if (DatabaseOperations.checkAuthentication(user, pass)) 
		{		
			((User) getApplication()).setUsername(user);
			Intent intent = new Intent(Login.this,
					HomeScreen.class);
			intent.putExtra(USER_NAME_KEY, user);
			startActivity(intent);
			Login.this.finish();	
			
		} 
		// But if the user is not registered in our database, we will display an error message.
		else 
		{
			
					   Toast.makeText(getApplicationContext(), "Incorrect Account ! ", 8).show();
				       
		}
	}

	}
	
	  public void Login1() {
		    Login1 task = new Login1();
		      task.execute();

		    }
}
