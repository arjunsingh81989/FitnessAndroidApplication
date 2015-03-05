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

import java.util.Comparator;

// This class needs to implement Comparator<G> interface so that we can call comapareTo method.
// ArchiveComparator will help us to list the dates, time, distance and calories burnt in sorted order in their 
// respective columns of the Archive Page.
public class ArchiveComparator implements Comparator<MyArchive> {

	// The type will determine how the list needs to be sorted -- descending or
	// ascending.
	private int type;

	@Override
	// Every MyArchive object in the list of MyArchives has four fields: date,
	// time, distance and calories associated
	// with it. Each of these fields can be sorted in two different ways:
	// Ascending or Descending. This means we have a
	// total of 8 different types : sort date in ascending, sort time in
	// descending etc.
	public int compare(MyArchive lhs, MyArchive rhs) {

		// This return value will allow the Collection.sort(..) method to decide
		// how two objects need to be sorted
		int ret = 0;

		switch (type) {

		// "Sort_Run_Date_ASCENDING" , "Sort_Run_Date_DESCENDING" are final
		// static int which are declared and given a
		// value in MyArchive.java.
		// compareTo method is already implemented in Date class so we can just
		// call it. This method will give us a
		// value grater than 0 if the left date object comes after the right
		// date object.
		case MyArchive.Sort_Run_Date_ASCENDING:
			ret = lhs.getDate().compareTo(rhs.getDate());
			break;
		case MyArchive.Sort_Run_Date_DESCENDING:
			ret = rhs.getDate().compareTo(lhs.getDate());
			break;

		// We can use similarly give a value to ret depending on how we want to
		// sort time, distance and calories burnt.
		case MyArchive.Sort_TimeSpent_ASCENDING:
			
			ret = lhs.getTime() > rhs.getTime() ? 1 : -1;
			
			break;
		
		case MyArchive.Sort_TimeSpent_DESCENDING:
		
			ret = lhs.getTime() > rhs.getTime() ? -1 : 1;
			
			break;
		
		case MyArchive.Sort_DistanceCovered_ASCENDING:
		
			ret = lhs.getDistance() > rhs.getDistance() ? 1 : -1;
			
			break;
		
		case MyArchive.Sort_DistanceCovered_DESCENDING:
		
			ret = lhs.getDistance() > rhs.getDistance() ? -1 : 1;
			
			break;
		
		case MyArchive.Sort_CaloriesBurnt_ASCENDING:
		
			ret = lhs.getkCal() > rhs.getkCal() ? 1 : -1;
			
			break;
		
		case MyArchive.Sort_CaloriesBurnt_DESCENDING:
		
			ret = lhs.getkCal() > rhs.getkCal() ? -1 : 1;
			
			break;

		default:
			break;
		}

		// Return the value for Collections.sort(...) method.
		return ret;
	}

	// I will set the type of the sorting style in the ArchiveActivity class where I will set the
	// default sort type to Sort_Run_Date_ASCENDING.
	public void setType(int type) {
		this.type = type;
	}

	// A getter method for the sorting style -- Not sure if I will use it anywhere. 
	public int getType() {
		return type;
	}

}
