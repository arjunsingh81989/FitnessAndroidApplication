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
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.activity.R;

// I am extending ArrayAdater because I need to implement the getView method for the archives screen. getView(...) method will return a view object 
// which will have all values -- distance, calories, time and date -- associated with the corresponding item in the mArchiveList.
// getView(...) method is called for every entry present in the list which is why we can see all the items in the mArchiveList as view objects on
// Archives Screen of the Android.
public class MyArchiveAdapter extends ArrayAdapter<MyArchive> {
	
	// This will store all the archives objects. Each archive object contains values of an old run.
	private List<MyArchive> mArchiveList;
	
	// I need this to create an inflater object for the getView(...). This activity points to ArchiveActivity.java which will help in
	// inflating -- changing activity_item.xml to java view object which will be given values for distance, calories, time , date depending 
	// on which item in mArchivesList is being used in the getView(...) method.
	private Activity activity;

	// The listview on the archives page will call the constructor of the MyArchiveAdapter class which will create ONE view object from ONE entry 
	// in the mArchiveList.
	public MyArchiveAdapter(Activity activity, List<MyArchive> archiveList) {
		super(activity, R.layout.archive_item, archiveList);
		this.mArchiveList = archiveList;
		this.activity = activity;
	}

	// Returns the number of items in this dataset.
	@Override
	public int getCount() {
	
		return mArchiveList.size();
	}

	// Returns the archives object present at the given position in the list. This method will be used in the
	// getView(...) so that we can retrieve the object's values -- distance, calories, time and date.
	@Override
	public MyArchive getItem(int position) {
	
		return mArchiveList.get(position);
	}

	// I will not use this method anywhere so just return 0.
	@Override
	public long getItemId(int position) {
	
		return 0;
	}

	// ViewHolder will help us to optimize the scrolling of the ListView by not
	// having to call the findViewByIdevery time a new row is loaded
	// on the sceen.
	public static class ViewHolder {

		// The following represent references to all the Views -- date, time ,
		// distance and calories --present in
		// a single row of the ListView.
		public TextView txtDate;
		
		public TextView txtTime;
		
		public TextView txtDistance;
		
		public TextView txtKcal;
	}

	// getView will be called for every row in the list: mArchiveList. It returns
	// the View for every row in the list.
	// Each View object has its own values for date, distance, time and
	// calories.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		

		// We need to create an inflator object so that we can change the archive_item.xml into a java view object. 
		// archive_item.xml defines the layout for every row in the list.
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Declaring holder object to store reference to all the textviews contained in the LinearLayout of the archive_item.xml
		ViewHolder holder;

		// This will store the result of the inflation of archive_item.xml -- It
		// will be Java View Object whose values can be changed 
		View view;
		
		// We will use this archive object at the given position to call getter methods for distance, calories, time and date .
		// This information would then be used in a view for this archive object.
		MyArchive archive = mArchiveList.get(position);

		// Creating the row for the very first time ? This means the user has
		// not scrolled down the screen to view rest of the items in the
		// archives list.
		// But for all the archive items which can be displayed on the screen we
		// will have to inflate the archive_item.xml for each of these items.
		if (convertView == null) {
			holder = new ViewHolder();
			
			view = (LinearLayout) inflater.inflate(R.layout.archive_item, null);
			
			holder.txtDate = (TextView) view.findViewById(R.id.date);
			
			holder.txtTime = (TextView) view.findViewById(R.id.time);
			
			holder.txtDistance = (TextView) view.findViewById(R.id.distance);
			
			holder.txtKcal = (TextView) view.findViewById(R.id.calories);

			// Store the viewholder object in the view object so that it could
			// be used while recycling. Recycling means that we can use
			// an old view object (which is not visible on the screen) for
			// creating a new view object. This new view object in our case
			// is an another item in the myArchive list. This will save us from
			// inflating the archive_item.xml repeatedly -- which is a very expensive operation.
			view.setTag(holder);
		} else {
			
			// We will store the holder by calling getTag() method which will save us from calling findViewById repeatedly. 
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		// Defining how we want our date to appear on the screen.
		SimpleDateFormat fmt = new SimpleDateFormat("yy:MM:dd");
		
		String date = fmt.format(archive.getDate());
		
		// txtDate is stored in the holder so we can use holder object to retrieve it and then set the text for date.
		holder.txtDate.setText(date);
		
		int hour = (int) (archive.getTime() / 3600);
		
		int min = (int) ((archive.getTime() - hour * 3600) / 60);
		
		int sec = (int) (archive.getTime() - hour * 3600 - min * 60);

		String time = String.format("%d:%d:%d", hour, min, sec);

		// Whether we are recycling or not we will always set the values of
		// txtDate, txtTime, txtDistance and txtKcal.
		holder.txtTime.setText(time);
		
		holder.txtDistance
				.setText(String.format("%.3fm", archive.getDistance()));
		
		holder.txtKcal.setText(String.format("%dkcal", archive.getkCal()));

		// Return the view object ( this is just one entry in the archives list) 
		return view;
	}

}