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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.demo.database.DatabaseOperations;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class ArchiveActivity extends Activity {

	// This is the url link of the gps folder on my Amazon ec2 instance.
	public static final String MY_SERVER_URL = "http://ec2-54-213-85-176.us-west-2.compute.amazonaws.com/gps/";
	// index.php uses PHP code to save the information (sent to it using
	// HttpPost) to the db30902 database's users table.
	public static final String MY_POST_FILE = "index.php";

	// Complete url address of our POST File -- index.php
	public static final String URL_STRING = MY_SERVER_URL + MY_POST_FILE;

	// This will display all archive objects in archiveList in the form of a
	// list.
	private ListView listView;

	// This will hold all the archive objects belonging to one particular user.
	// Each archive object contains
	// information about the calories burnt, time spent running, distance
	// covered by the runner and the date of the run.
	private List<MyArchive> archiveList;

	// The 'type' defines the sorting style -- ascending or descending for each
	// of the archive fields -- date, time, calories and distance.
	private int type;

	// The method is called when this activity runs .
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// archive_activity is an xml file and it defines the layout of the
		// Archives Screen.
		setContentView(R.layout.archive_activity);
		// archiveList is the list view created in the archive_activity xml
		// file.
		// We will create a Java View object by calling findViewById on its id
		// given in the R.java file.
		listView = (ListView) findViewById(R.id.archiveList);
		// We will use an arraylist for storing all the archive objects of a
		// given user.
		archiveList = new ArrayList<MyArchive>();

		// It may take few seconds to fetch the data from the archives table of
		// our database db30902 ( and sort
		// all the entries on the Archives Screen) so during this
		// time we can show an Android Progress Dialog with the message
		// "Loading Archives".
		final ProgressDialog dialog = new ProgressDialog(this);

		dialog.setMessage("Loading archives...");

		dialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// We need the username so that the app will retrieve all the
				// archives objects belonging to this user only.
				final String userName = getIntent().getExtras().getString(
						Login.USER_NAME_KEY);
				// Give that username to getArchiveList which will return us a
				// list of all archive objects.
				try {
					archiveList = DatabaseOperations.getArchiveList(userName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (archiveList != null) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// When the archives page is first loaded it will be
							// sorted by the type = Sort_Run_Date_ASCENDING.
							sort(MyArchive.Sort_Run_Date_ASCENDING);

							// Dismiss the progress dialog after all the entries
							// have been sorted
							dialog.dismiss();
						}
					});
				}

			}
		}).start();

	}

	// This method with send the information needed to retrieve the past run information about a given user.
	// The server will send back a JSON string which will be in the format of NameValue pairs.
	public static String getJSONStringFromUrl(String url,List<NameValuePair> params) {

		// An object of HttpClient will execute the post request to the server.
		HttpClient httpclient;
		// We will use HttpPost to send the information as NameValue pairs to the server.
		HttpPost httppost;
		
		// This is the JSON string that will sent back from the server.
		String responseString = "";
		try {

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(url);
			// setEntity(..) will give the data -- which in the form of NameValuePairs -- to the given object: httppost . 
			httppost.setEntity(new UrlEncodedFormEntity(params));
			// This handler will allow us to retrieve the response as String.
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			// Now, we will execute the post which will send the information -- contained in the post object -- to the server.
			// After the execution of the request, the server will send a response in the form of a string.
			responseString = httpclient.execute(httppost, responseHandler);
			
			
			// We an view the jsong string representation on the console.
			System.out.println("Response : " + responseString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the response string 	
		return responseString;
	}

	
	// This method is called in two places: onClick(View) and run(). 
	// The type determines how we want to sort the entries: Sort_Run_Date_ASCENDING, Sort_Run_Date_DESCENDING, Sort_TimeSpent_ASCENDING etc
	// This type can be retrieved only in the onClick method depending on which view : Date , time, calories, distance is clicked 
	// by the user. 
	// In the run method i will give it a default sort value: Sort_Run_Date_ASCENDING -- this is the sort type. So, when the archives
	// page is loaded all the entries will be sorted by Sort_Run_Date_ASCENDING -- old dates will be at the top. 
	private void sort(int type) {
		
		// Each View (Date, Time, Calories, Distance) is divided up into a TextView and an ImageView which are arranged in a 
		// LinearLayout. The TextView will hold the simple text like Date, Time, Calories and Distance.
		// The ImageView will hold the arrow_up or arrow_down image depending on the sorting type requested by the
		// user. Since, in the run() method the default sorting type is Sort_Run_Date_ASCENDING, the Date view will
		// have an arrow_up image visible to the user.
		
		
		// The following four lines will make all the four ImageViews in their respective views invisible to the user.
		// I did this because when the onClick method is called then the default run_date_ascending sort type should
		// become invisible and the image should be determined by the view which was clicked by the user.
		((ImageView) findViewById(R.id.dateIv)).setVisibility(View.GONE);
		
		((ImageView) findViewById(R.id.timeIv)).setVisibility(View.GONE);
		
		((ImageView) findViewById(R.id.distanceIv)).setVisibility(View.GONE);
		
		((ImageView) findViewById(R.id.caloryIv)).setVisibility(View.GONE);
		
		// The value of type will come either from the run() or onClick method.
		switch (type) {
		
		// These are all final static int declared and initialized in MyArchive.java. 
		// We will use these int values to determine which image : arrow_up or arrow_down should be used
		// in what View of the archives page.
		case MyArchive.Sort_Run_Date_ASCENDING:
			
			// First make the ImageView visible
			((ImageView) findViewById(R.id.dateIv)).setVisibility(View.VISIBLE);
			
			// and then place the arrow_up image in the Date view's ImageView section. The arrow_up
			// and arrow_down images have been added in the /res/drawable folder of this project.
			((ImageView) findViewById(R.id.dateIv))
					.setImageResource(R.drawable.arrow_up);
			break;
		
	// Similarly we can set arrow_up or arrow_down images in the ther views of the archives page -- depending on which
	// view is clicked by the user.
		case MyArchive.Sort_Run_Date_DESCENDING:
		
			((ImageView) findViewById(R.id.dateIv)).setVisibility(View.VISIBLE);
			
			((ImageView) findViewById(R.id.dateIv))
					.setImageResource(R.drawable.arrow_down);
			break;
		
		case MyArchive.Sort_TimeSpent_ASCENDING:
		
			((ImageView) findViewById(R.id.timeIv)).setVisibility(View.VISIBLE);
			
			((ImageView) findViewById(R.id.timeIv))
					.setImageResource(R.drawable.arrow_up);
			break;
		
		case MyArchive.Sort_TimeSpent_DESCENDING:
		
			((ImageView) findViewById(R.id.timeIv)).setVisibility(View.VISIBLE);
			
			((ImageView) findViewById(R.id.timeIv)).setImageResource(R.drawable.arrow_down);
			
			break;
		
		case MyArchive.Sort_DistanceCovered_ASCENDING:
			
			((ImageView) findViewById(R.id.distanceIv)).setVisibility(View.VISIBLE);
			((ImageView) findViewById(R.id.distanceIv))
					.setImageResource(R.drawable.arrow_up);
			break;
		
		case MyArchive.Sort_DistanceCovered_DESCENDING:
		
			((ImageView) findViewById(R.id.distanceIv))
					.setVisibility(View.VISIBLE);
			
			((ImageView) findViewById(R.id.distanceIv))
					.setImageResource(R.drawable.arrow_down);
			break;
		
		case MyArchive.Sort_CaloriesBurnt_ASCENDING:
		
			((ImageView) findViewById(R.id.caloryIv))
					.setVisibility(View.VISIBLE);
			
			((ImageView) findViewById(R.id.caloryIv))
					.setImageResource(R.drawable.arrow_up);
			break;
		case MyArchive.Sort_CaloriesBurnt_DESCENDING:
			
			((ImageView) findViewById(R.id.caloryIv))
					.setVisibility(View.VISIBLE);
			
			((ImageView) findViewById(R.id.caloryIv))
					.setImageResource(R.drawable.arrow_down);
			
			break;
		}
	
		this.type = type;
		
	// The list needs to have archive objects to sort
		if (archiveList != null) {
		
			// This is my custom comparator class which has a compare( lhsArchive, rhsArchive) method
			// that returns an int value after comparing ome archive object with another. This int value is required for
			// the Collections.sort(.) method
			ArchiveComparator comparator = new ArchiveComparator();
			
			//  We need to out sort type value to the comparator object so that the appropriate getter method-- getDate, getTime is called
			// is called in the ArchiveComparator.java class. This is help us to call the already implemented compare(..)
			// method for their datatype
			comparator.setType(type);
			
			// Call the static sort method of the Collections class that takes an instance of a comparator object as an 
		   // argument. This sort is dependent on the return value of the compare (lhsArchive, rhsArchive)
			Collections.sort(archiveList, comparator);
			
			// I need MyArchiveAdapter class because I want to extend the ArrayAdapter class which will allow me to write my
			// own implementation of getView(..) method. I need my own implementation of getView method because in our case
			// the list consist of archive objects which are created by using the MyArchive.java class. getView allows us to
			// set the values of our view objects -- in our case these view objects are the archive objects.
			MyArchiveAdapter adapter = new MyArchiveAdapter(
					ArchiveActivity.this, archiveList);
			
			// listView object defines the layout of our list of archive objects. So we will therefore set the MyArchiveAdapter on it. 
			listView.setAdapter(adapter);
		}

	}

	
	// The archives page has four main Views : Date, Time, Calories and Distance. For every view we have two different styles of sorts:
	// ascending or descending. Ascending will be shown by an arrow_up image and descending with an arrow_down image.
	public void onClick(View view) {
		
	// Get the if form the R.java file so that we know which view has been clicked and what switch case to enter.
		switch (view.getId()) {
		
		// I have added a home_icon  on the upper left corner of the archive screen and made it a button in archive_activity.xml file.
		// This button will take us back to the HomeScreen 
		case R.id.backbtn:
		// and finish the ArchiveActivity.
			this.finish();
			
			break;
		// If the Date view is clicked, then we need to first check what sort type it is to. If its ascending then that means that
	    // currently all the date entries are arranged in the ascending order -- old dates are at the top of the listView. So if the user
		// clicks the Date View then we will change the sort type to descending.
		case R.id.date:
		
			if (type == MyArchive.Sort_Run_Date_ASCENDING) {
			
				type = MyArchive.Sort_Run_Date_DESCENDING;
			
			} else {
			
				type = MyArchive.Sort_Run_Date_ASCENDING;
			}
			
			// After setting the sort type we can now call the static sort(type) method which will use the ArchiveComparator class 
			// to sort the entries -- ascending or descending -- in the Date column.
			sort(type);
			
			break;
		// We will follow the same steps for time, calories and distance.
		case R.id.time:
		
			if (type == MyArchive.Sort_TimeSpent_ASCENDING) {
			
				type = MyArchive.Sort_TimeSpent_DESCENDING;
			
			} else {
			
				type = MyArchive.Sort_TimeSpent_ASCENDING;
			}
			
			sort(type);
			
			break;
		
		case R.id.distance:
		
			if (type == MyArchive.Sort_DistanceCovered_ASCENDING) {
			
				type = MyArchive.Sort_DistanceCovered_DESCENDING;
			
			} else {
			
				type = MyArchive.Sort_DistanceCovered_ASCENDING;
			}
		
			sort(type);
			
			break;
		
		case R.id.calories:
		
			if (type == MyArchive.Sort_CaloriesBurnt_ASCENDING) {
			
				type = MyArchive.Sort_CaloriesBurnt_DESCENDING;
			
			} else {
			
				type = MyArchive.Sort_CaloriesBurnt_ASCENDING;
			}
			
			sort(type);
			
			break;
		
		default:
			break;
		}
	}

}
