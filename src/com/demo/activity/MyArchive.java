
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

import java.util.Date;

public class MyArchive {
	
	// Date, time, distance, Kcal are values that will be used to construct an archive object. An archive object stores the given info
	// of previous run.
	private Date date;
	private long time;
	private float distance;
	private int kCal;
	
	// The following are the Sort types which will determines how the entries in the Date, Time, Calories and Distance columns
	// of the Archives Page should be sorted.
	public final static int Sort_Run_Date_ASCENDING = 1;
	
	public final static int Sort_Run_Date_DESCENDING = 2;
	
	public final static int Sort_TimeSpent_ASCENDING = 3;
	
	public final static int Sort_TimeSpent_DESCENDING = 4;
	
	public final static int Sort_DistanceCovered_ASCENDING = 5;
	
	public final static int Sort_DistanceCovered_DESCENDING = 6;
	
	public final static int Sort_CaloriesBurnt_ASCENDING = 7;
	
	public final static int Sort_CaloriesBurnt_DESCENDING = 8;
	
	// Construct an object of Archives class. This object will be placed in a list which will be a part of the listView.
	public MyArchive(Date date, long time, float distance, int kCal) {
		super();
		this.date = date;
		this.time = time;
		this.distance = distance;
		this.kCal = kCal;
	}
	/**
	 * @return the run date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the run date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the time spent while running
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param time the run time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * @return the distance covered by the runner.
	 */
	public float getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(float distance) {
		this.distance = distance;
	}
	/**
	 * @return the kCal burnt by the runner.
	 */
	public int getkCal() {
		return kCal;
	}
	/**
	 * @param kCal the kCal to set
	 */
	public void setkCal(int kCal) {
		this.kCal = kCal;
	}
}
