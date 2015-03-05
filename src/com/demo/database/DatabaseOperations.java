/*
 
    Aurhor of DatabaseOperations.java : Arjun Singh
 
 */

package com.demo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.demo.activity.MyArchive;
import com.demo.activity.User1;

public class DatabaseOperations
{
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String SERVER = "jdbc:mysql://mysql.cs.iastate.edu:3306/";
	private static final String USER_DATA_TABLE = "USER_DATA";
	private static final String AUTHENTICATION_TABLE = "AUTHENTICATION";
	private static final String UPGRADE_TABLE = "UPGRADE";
	private static final String USER_SETTINGS_TABLE = "USER_SETTINGS";
	private static final String DB_NAME = "db30902";
	private static final String USER = "adm309";
	private static final String PASSWORD = "EXbDqudt4";
	
	
	public static List<User1> getUserList() {
		List<User1> users = new ArrayList<User1>();
		try {
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER,
					PASSWORD);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM AUTHENTICATION");
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String pass = rs.getString(3);
				String type = rs.getString(4);
				User1 user = new User1(id, name, pass, type);
				users.add(user);

			}
		} catch (Exception ex) {
			Log.e("Database", "Error: " + ex.getMessage());
		}
		return users;
	}
	
	
	public static List<String> getFriendList(int userId) {
		List<String> friendList = new ArrayList<String>();
		try {
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER,
					PASSWORD);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(String.format(
					"SELECT FRIENDS FROM USER_SETTINGS WHERE USER_ID = %d", userId));
			if (rs.next()) {
				String friends = rs.getString(1);
				if (friends != null) {
					String[] temps = friends.split(","); /// ------- COMMA
					
					for (String string : temps) {
						friendList.add(string);
						Log.e("Database", "Friend: " + string);
					}
				}
			}
		
		rs.close();
		connection.close();
		} catch (Exception ex) {
			
			

		}
		return friendList;
	}
	
	
	
	public static void changeUserPassword(String USERNAME, String newPassword) throws Exception
	{
		
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			
			statement = connection.createStatement();
			int userID = getUserID(USERNAME);
			
			
			statement.executeUpdate("UPDATE " + AUTHENTICATION_TABLE + " SET PASSWORD = '" + newPassword + "' WHERE USER_ID = '" + userID + "'");

						connection.close();
		}
		catch(Exception ex)
		{
			throw ex;
		}	
		
		
	}
	
	
	
	public static void deleteAccount(String USERNAME) throws Exception
	{
		
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER+DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			int userID = getUserID(USERNAME);
			
			statement.addBatch("DELETE FROM AUTHENTICATION WHERE USER_ID = '" + userID + "'");
			statement.addBatch("DELETE FROM UPGRADE WHERE USERNAME = '" + USERNAME + "'");
			statement.addBatch("DELETE FROM USER_DATA WHERE USERNAME = '" + USERNAME + "'");
			statement.addBatch("DELETE FROM USER_SETTINGS WHERE USER_ID = '" + userID + "'");
		  
			int[] results = statement.executeBatch();
		}
		
		   catch (Exception ex)
			{
				throw ex;			
			}
		
		
		}
	
	
	public static HashMap<String, Object> getRunById(int id) throws Exception
	{
		HashMap<String, Object> runData = new HashMap<String, Object>();
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER+DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM " + USER_DATA_TABLE + " WHERE RUN_ID='"+ id + "'");
			if(resultSet.first())
			{
				runData.put("RUN_ID", resultSet.getObject("RUN_ID"));
				runData.put("USERNAME", resultSet.getObject("USERNAME"));
				runData.put("DISTANCE", resultSet.getObject("DISTANCE"));
				runData.put("DATE", resultSet.getObject("DATE"));
				runData.put("WEIGHT", resultSet.getObject("WEIGHT"));
				runData.put("HEIGHT", resultSet.getObject("HEIGHT"));
			}
			
			connection.close();
			
			return runData;
		}
		catch (Exception ex)
		{
			throw ex;			
		}
	}
	
	public static void UserInfoInUserSettingsTableAdded(String username) throws Exception
	{
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			int userID = getUserID(username);
			
			statement.executeUpdate("INSERT INTO " + USER_SETTINGS_TABLE + "(USER_ID) VALUES ('" + userID +  "');");			

			connection.close();
		}
		catch(Exception ex)
		{
			throw ex;
		}	

	}
	
	
	public static void Add(String AGE, String GENDER, String CURRENT_WEIGHT_KG,  String GOAL_WEIGHT_KG,String HEIGHT_FT  ,  String USERNAME  )    throws Exception
	{
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			String kg = "23";
			String lbs= ""+2*Integer.parseInt(kg);
			System.out.println(lbs);
			
			statement = connection.createStatement();
			int userID = getUserID(USERNAME);
			
			String CURRENT_WEIGHT_LBS = ""+ 2 * Integer.parseInt(CURRENT_WEIGHT_KG);
			String GOAL_WEIGHT_LBS = ""+  2 * Integer.parseInt(GOAL_WEIGHT_KG);
			String HEIGHT_IN  = "" + 12 * Integer.parseInt(HEIGHT_FT);
			 String HEIGHT_CM = ""+ 30.48 * Integer.parseInt(HEIGHT_FT);
			
			statement.executeUpdate("UPDATE " + USER_SETTINGS_TABLE + " SET AGE = '" + AGE + "', GENDER = '" + GENDER + "', CURRENT_WEIGHT_LBS = '" + CURRENT_WEIGHT_LBS + "', CURRENT_WEIGHT_KG = '" + CURRENT_WEIGHT_KG + "', GOAL_WEIGHT_LBS = '" + GOAL_WEIGHT_LBS + "', GOAL_WEIGHT_KG = '" + GOAL_WEIGHT_KG + "', HEIGHT_IN = '" + HEIGHT_IN + "', HEIGHT_FT = '" + HEIGHT_FT + "', HEIGHT_CM = '" + HEIGHT_CM + "' WHERE USER_ID = '" + userID + "'");
			connection.close();
		}
		catch(Exception ex)
		{
			throw ex;
		}	

	}

	public static  int getUserID(String username) throws Exception
	{
		 int userID = 0; 
		try
				{
					Class.forName(DRIVER).newInstance();
					connection = DriverManager.getConnection(SERVER+DB_NAME, USER, PASSWORD);
					
					statement = connection.createStatement();
			
					
					resultSet = statement.executeQuery("SELECT USER_ID FROM " +  AUTHENTICATION_TABLE + " WHERE USERNAME='"+ username + "'");
			
					if(resultSet.next()){
						 userID = resultSet.getInt("USER_ID");

						}
				//	connection.close();	 
					return userID; 
				}
			
			  catch (Exception ex)
				{
					throw ex;			
				}

		
		

	}
	
	
	
	
	
	/*
	 
	 *   getArchiveList method called in ArchiveActivity.java has been modified to use JDBC. This method initially
	 *   used PHP.
	 *   
	 *   The Archives Screen also has a column for Calories Burned. Right now there is no Calories Burned 
	 *   column in the USER_DATA table so the Archives Screen will display a default value of 0 for calories burned (for all users).
	 *   
	 *   To test Archives Android page login using username and password given in the
	 *   AUTHENTICATION table. 
	 *   
	 *   Note: The username must have one or more records in the  USER_DATA table.
	 *   
	 */
	public static  List<MyArchive> getArchiveList(String username) throws Exception
	{
	
		  List<MyArchive> archives = new ArrayList<MyArchive>();
		  
		  try
			{
				Class.forName(DRIVER).newInstance();
				connection = DriverManager.getConnection(SERVER+DB_NAME, USER, PASSWORD);
				
				statement = connection.createStatement();
		
				SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resultSet = statement.executeQuery("SELECT * FROM " +  USER_DATA_TABLE + " WHERE USERNAME='"+ username + "'");
		
				 while (resultSet.next()) {
			          
				
					
					 String dateText = resultSet.getString("DATE");
					 long time = resultSet.getLong("DURATION");
					 int calories =0;
					 float distance = Float.parseFloat(resultSet
								.getString("DISTANCE"));
					 
					 MyArchive archive = new MyArchive(d.parse(dateText), time,
								distance, calories);
						
						
						archives.add(archive);
	
				 }

			}
		
		  catch (Exception ex)
			{
				throw ex;			
			}
		return archives;
		
	}
		
	// ---------------------------------- METHOD ENDS -------------------------------------------------- //
	
	

	
	
	public static void putRunData(String username, double distance, int weight, int height, double[] longitude, double[] latitude) throws Exception
	{
		String runId = UUID.randomUUID().toString();
		String longJSON = doubleArrayToJSON(longitude);
		String latJSON = doubleArrayToJSON(latitude);
		
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO " + USER_DATA_TABLE + "(RUN_ID, USERNAME, DISTANCE, WEIGHT, HEIGHT, LONGITUDE, LATITUDE) VALUES ('" + runId + "','" + username + "','" + distance + "','" + weight + "','" + height + "','" + longJSON + "','" + latJSON + "');");			
		}
		catch(Exception ex)
		{
			throw ex;
		}		
	}
	
	public static boolean checkAuthentication(String username, String password) throws Exception
	{
		HashMap<String, Object> userData = new HashMap<String, Object>();
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM " + AUTHENTICATION_TABLE + " WHERE USERNAME='"+ username + "'");
			
			if(resultSet.first())
			{
				userData.put("PASSWORD", resultSet.getObject("PASSWORD"));
			}
			
			connection.close();
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
		if(userData.get("PASSWORD").equals(password))
		{
			return true;
		}
		else
		{
			return false;
		}		
	}
	
	public static void putUpgrade(String cardType, String cardNumber, String expirationDate, String securityCode, String username) throws Exception
	{
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO " + UPGRADE_TABLE + "(USERNAME, CARD_TYPE, EXPIRATION_DATE, SECURITY_CODE, CARD_NUMBER) VALUES ('" + username + "','" + cardType + "','" + expirationDate + "','" + securityCode + "','" + cardNumber + "');");			
		}
		catch(Exception ex)
		{
			throw ex;
		}	
	}
	
	public static boolean checkUserExists(String username) throws Exception
	{
		boolean exists = false;
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM " + AUTHENTICATION_TABLE + " WHERE USERNAME='"+ username + "'");
			
			if(resultSet.first())
			{
				exists = true;
			}
			
			connection.close();
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
		if(exists)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void putUser(String username, String password, String type) throws Exception
	{
		try
		{
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(SERVER + DB_NAME, USER, PASSWORD);
			
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO " + AUTHENTICATION_TABLE + "(USERNAME, PASSWORD, USER_TYPE) VALUES ('" + username + "','" + password + "','" + type + "');");			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
	}
	
	private static String doubleArrayToJSON(double[] data) throws JSONException
	{
		JSONArray j = new JSONArray();
		
		for(int i = 0; i < data.length; i++)
		{
			j.put(data[i]);
		}
		
		return j.toString();
	}

}
