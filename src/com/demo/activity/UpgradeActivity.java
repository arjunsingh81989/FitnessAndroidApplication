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

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.demo.database.DatabaseOperations;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class UpgradeActivity extends Activity {

	// I need spinners for displaying dropdown menu for the Card Type
	private Spinner spinner;

	// date will store the expiration date of the Card as a String. Expiration
	// date is one of the columns
	// of the upgrade table of db30902.
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// When this activity is started from the HomeScreen Intent then the
		// contents of the screen
		// will be set to an Android xml file: upgrade_activity present in the
		// /res/layout folder.
		// This file has the xml code for the layout of the Upgrade Screen.
		setContentView(R.layout.upgrade_activity);

		// Create a spinner object so that we could setup an array adapter for
		// storing multiple values
		// in it.
		spinner = (Spinner) findViewById(R.id.cardType);

		// I created Arrays.xml file and added the three values for the Card
		// Type : American Express, Discovery and Visa.
		// This xml file is present in the /res/values folder. When this file is
		// created android will automatically update
		// the R.java file contained in the gen folder of the project. So we can
		// use the R file to setup the array adapter
		// for the drop down menu.
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.card_type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		// This is an "I agree to the terms and conditions" checkbox. Checking
		// this box will enable the Update button.
		CheckBox ckb = (CheckBox) findViewById(R.id.agree);

		// This is the Upgrade button which will send the user's payment
		// information to the PHP file on the server.
		// PHP file will insert the card information to the Upgrade table.
		final Button upgrade = (Button) findViewById(R.id.upgradeBtn);

		// I need to setup an "OnChecked" Listener which will enable the Upgrade
		// button when the box is checked.
		ckb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// Enable the upgrade button when the box is checked.
				upgrade.setEnabled(isChecked);

			}
		});

	}

	// I will call this function in the /res/layout/upgrade_activity.xml file.
	// This way I dont need to implement
	// any onClickListeners in this java file.
	public void onClick(View view) throws Exception {

		// Determine what view is clicked based on its id in the /gen/R.java
		// file
		switch (view.getId()) {
		// And use it decide what method to call.

		// If upgrade button is clicked then call the upgrade method present in
		// this java file.
		// The method will send the information as NameValue Pairs to the
		// server.
		case R.id.upgradeBtn:
			upgrade();
			break;

		// This button is on the upper-left corner and its back_icon.png image
		// is present in the /res/drawable folder
		case R.id.backbtn:

			// Finish the current activity (and go back to the intent which
			// started this activity) if the back button is
			// clicked. Again, I will call the onclick method for this button in
			// its ImageView in
			// the /res/layout/upgrade_activity.xml file
			this.finish();
			break;

		default:
			break;
		}
	}

	// The method is called when the Upgrade button is clicked by the user. It
	// will use a static method:addUpgrade
	// which will use JSON_Obj object to send card information as NameValue
	// Pairs.
	private void upgrade() throws Exception {
		// We need this int value to serve an index because card_type is an
		// array defined in /res/values/Arrays.xml file.
		int selectedItem = spinner.getSelectedItemPosition();

		// Use the index -- selectedItem -- to retrieve the string value of the
		// card_type.
		final String cardType = getResources()
				.getStringArray(R.array.card_type)[selectedItem];

		// Retrieve card code and security values using their corresponding id
		// values present in the /gen/R.java file.
		// Their id values are created when respective EditTexts are saved in
		// the xml file.
		final String cardCode = ((EditText) findViewById(R.id.card)).getText()
				.toString();
		final String security = ((EditText) findViewById(R.id.security))
				.getText().toString();

		// The HomeScreen Intent starts the upgrade activity. In the
		// HomeScreen.java file I have given it
		// an additional value of USER_NAME_KEY which will contain the name of
		// the user. This key is defined (publicly)
		// and given a value in the Login.java file.
		final String userName = getIntent().getExtras().getString(
				Login.USER_NAME_KEY);

		// Finally, call the static method with the information retrieved from
		// the upgrade screen.
		DatabaseOperations.putUpgrade(cardType, cardCode, date, security, userName);

	}

	// I will call this function in the upgrade_activity.xml file under the
	// "expireDate" button. So when
	// the expiryDate button is clicked the user can scroll and select the
	// numeric date, string month and numeric year.

	public void showDatePickerDialog(View v) {
		DialogFragment expiryDate = new DateSelector();
		expiryDate.show(getFragmentManager(), "dateSelectorr");
	}

	// I am extending DialogFragment because I want to create my own dialog
	// which in this case will be a date selector
	// dialog fragment -- allowing the user to select a date from the dialog.
	// Also, I will implement an
	// interface -- DatePickerDialog.OnDateSetListener which will allow me
	// to write the implementation of the onDateSet. OnDateSet is useful because
	// it will allow us to perform
	// operations once the user has selected the date or dismissed the date
	// selector dialog box. In this case, I will
	// I will use this method to initialize the "date" variable which is used by
	// the addUpgrade static method. Also,
	// I will set the text of the expire date's EditView box to the date
	// selected by the user from the dialog.

	class DateSelector extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		// I will override the onCreateDialog method of the DialogFragment class
		// so as to define our own custom date selector
		// dialog box. When the user clicks on the expire date view then the
		// date picker dialog box ' will be made ' to open
		// with default date.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// We need to use a Calendar object to get the current values of
			// year, month and day.
			final Calendar c = Calendar.getInstance();
			// year, month and day will give us the current date.
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// So, now we only need to return the object which will display the
			// current date when the date picker dialog
			// is opened.
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		// This method is called once the user selects the date.
		public void onDateSet(DatePicker view, int year, int month, int day) {

			// Retrieve the month (from the array) which the user selected from
			// the DateDialog. I need this
			// so that the EditText will show the month in alphabets after the
			// date has been selected by the user.
			String strMonth = (new DateFormatSymbols(Locale.US)).getMonths()[month];

			// Define the format in which the data should be represented in the
			// upgrade table's expiry_date column.
			date = String.format("%d-%d-%d", year, month, day);

			// After the DateDialog has been dismissed, we can now put the date
			// as a text in the expireDate view.
			((Button) UpgradeActivity.this.findViewById(R.id.expireDate))
					.setText(String.format("%d %s %d", year, strMonth, day));
		}
	}

}
