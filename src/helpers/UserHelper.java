//MLC USER HELPER

package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import model.Admin;
import model.DbConnect;
import model.User;


/**
 * This class is used to insert, retrieve, and update users in the user table.
 */
public class UserHelper {
	
	/**
	 * Prepared SQL statement (combats: SQL Injections)
	 */
	private PreparedStatement authenticateUserStatement;
	private Connection connection;
	private ResultSet results;
	//private int numRecords;

	/**
	 * Constructor which makes a connection
	 */
	public UserHelper() {
			
			// set up the driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				// hard coded the connection in DbConnect class
				this.connection = DbConnect.devCredentials();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	}
	
	/**
	 * Authenticates a user 
	 * @return A user object if successful, null if unsuccessful.
	 */
	public User authenticateUser(String myID, String encryptedPass) {
		User su = new User(); //set to null to return null in case not authenticated
		//su = null; ///GINGER HERE UNCOMMENTED THIS
		
		System.out.println("UserHelper: authentication myID = " + myID); //MyID the student logged in with
		
		boolean valid = false; //assume not valid

		//--------------------------------------------
		//TODO
		// call some authentication server/code to check if UGA student w/ login name and password
		// send it myID and loginPassword
		// get back first name, last name, and email back from authentication server
		// returns w/ data or null
		//--------------------------------------------
		
		//purely for testing, set the valid to true until authentication in place
		valid = true;
		//valid = false;
		
		String studentUserMyID = "ganix";
		String studentUserFirstName = "Ginger";
		String studentUserLastName = "Nix";
		String userEmail = "ganix@uga.edu";
		
		System.out.println("UserHelper: auth userMyID = " + studentUserMyID);
		
		//if true, set user object info and return login user data
		if (valid){
			//User su = new User(); //GINGER UNCOMMENTED THIS
			//put all the student's data into the login user object
			su.setMyID(studentUserMyID);
			su.setUserLastName(studentUserLastName);
			su.setUserFirstName(studentUserFirstName);
			su.setUserEmail(userEmail);	
			System.out.println("UserHelper: auth in valid if userMyID = " + studentUserMyID);
		}		
		System.out.println("UserHelper: auth su = " + su);
		return su; //will be null if user wasn't valid
	}
	
	public boolean inUserTable(String myID){
		
		
		System.out.println("UserHelper inUserTable: myID = " + myID);
		
		String query = "SELECT * from tomcatdb.User WHERE myID = myID";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);

			this.results = ps.executeQuery();
			if (this.results.next()) {//the myID is already being used
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: inStudentTable method. Query = " + query);
		}
		
		return false;
	}

	public void updateLastLogin(String myID) {
		System.out.println("UserHelper: updateLastLogin myID = " + myID);
		String today = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		today = dateFormat.format(date);
		System.out.println(dateFormat.format(date)); //2014/08/06  
		System.out.println(today); //2014/08/06  
		
		String query = "Update tomcatdb.user SET user.lastLogin = '" + today + "' WHERE myID = '" + myID + "'";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			ps.executeUpdate();
			System.out.println("Success in UserHelper.java: updateLastLogin method. Query = " + query);
			System.out.println("Success in UserHelper.java: updateLastLogin method. myID = " + myID);
			System.out.println("Success in UserHelper.java: updateLastLogin method. today = " + today);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in UserHelper.java: updateLastLogin method. Query = " + query);
		}
		
	}
	
	public void insertUserTable(String myID, String fname, String lname, String email) {

		System.out.println("uh: insertUserTable");   

		String today = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		today = dateFormat.format(date);
		
		String query = "INSERT INTO tomcatdb.User (myID, fname, lname, email, lastLogin) VALUES ('" + myID + "','" + fname + "','" + lname + 
				"','" + email + "','"+ today + "')";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			ps.executeUpdate();
			System.out.println("Success in UserHelper.java: insert into table method. Query = " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in UserHelper.java: insert into table method. Query = " + query);
		}
		
	}
	
	
	public int getRecordID(String myID){
		int recordID = 0;
		String query = "SELECT userID FROM tomcatdb.User WHERE myID = '" + myID + "'";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
	
			ps.executeQuery();
			System.out.println("Success in UserHelper.java: get record ID method. Query = " + query);
			
			recordID = results.getInt("userID");

		} catch (SQLException e) {
			e.printStackTrace();
		System.out.println("***Error in UserHelper.java: get record ID method. Query = " + query);
			}
		
		return recordID;
		
	}
	

	//WRITE THIS TO QUERY Reservations TABLE AND RETURN NUM RECDS FOUND
	// this method is called to get the number of records a user has
	public int getNumberRecords(int userRecordID){
		
		
		//this.numRecords = 0;
		
		System.out.println("uh: list user reservations");   

		//get today's date to list the reservations today or later
		String currentDate = "";	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();	
		currentDate = dateFormat.format(date);
		
		//get all reservations for today and forward where user is primary or secondary - can't search on time yet
		// and the room is free (free = 0) ( which means they didn't fail to check into the room and they didn't cancel the reservation)
		//and the room info for the reservation
		// and the date is today or later (will have to deal with time later)
		
		String query = "SELECT * FROM tomcatdb.Reservations, tomcatdb.Rooms WHERE Reservations.Rooms_roomID = Rooms.roomID"
				+ " AND (Reservations.primaryUser = '" + userRecordID + "' OR Reservations.secondaryUser = '" + userRecordID + "')"
						+ " AND Reservations.free = 0"
						+ " AND Reservations.reserveStartDate >= '" + currentDate + "'";
		
		System.out.println("uh list user resv query " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			//ps.executeQuery();
			this.results= ps.executeQuery();
			System.out.println("Success in UserHelper.java: list user reservations method. Query = " + query);
			
			//get returned results, which may be more than just one records
			// go through all the results returned, if any
			
			
			if (!results.next() ) {
				   System.out.println("no data");
				  // System.out.println("== 1 In UserHelper.java: list user reservations method: num recds should be 0 = "+ this.numRecords);
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in UserHelper.java: get number of records. Query = " + query);
		}
		
		
		
		
		return 0;
		
		
	}
	
	public boolean alreadyBanned(int userID){

		System.out.println("UserHelper inBannedTable: myID = " + userID);
		
		String query = "SELECT * from tomcatdb.banned WHERE User_userID = ? "
					+ "AND banEnd IS NULL "
					+ "AND status = ? "
					+ "AND penaltyCount > ? "
					+ "LIMIT 1";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setInt(2, 1);
			ps.setInt(3, 1);
			
			this.results = ps.executeQuery();
			if (this.results.next()) {
				return true; //they are already banned for this current period
			}else{
				return false; //they have not been banned
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: already Banned method. Query = " + query);
		}
		
		return false;
	}


	/**
	 * Authenticates an admin user 
	 * @return An admin user object if successful, null if unsuccessful.
	 */
	public Admin authenticateAdminUser(String myID, String encryptedPass) {
		Admin adminUser = new Admin(); //set to null to return null in case not authenticated
	
		boolean valid = false; //assume not valid

		//--------------------------------------------
		//TODO
		// call some authentication server/code to check if UGA student w/ login name and password
		// send it myID and loginPassword
		// get back first name, last name, and email back from authentication server
		// returns w/ data or null
		//--------------------------------------------
		
		//purely for testing, set the valid to true until authentication in place
		valid = true;
		//valid = false;
		
		String adminUserMyID = "ganix";
		
		
		//if true, set user object info and return login user data
		if (valid){
			
			adminUser.setAdminMyID(adminUserMyID);
			System.out.println("UserHelper: auth in valid if userMyID = " + adminUserMyID);
		}		

		return adminUser; //will be null if user wasn't valid
	}
	
	public boolean inAdminUserTable(String myID){
		
		
		String query = "SELECT * from tomcatdb.Admin WHERE adminMyID = '" + myID + "' AND adminStatus = 1 LIMIT 1";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);

			this.results = ps.executeQuery();
			if (this.results.next()) {
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: inAdminUserTable method. Query = " + query);
		}
		
		return false;
	}
	
	public Admin getAdminInfo(String myID) {
		
		Admin adminUser = new Admin();
		
		String query = "SELECT * FROM tomcatdb.Admin WHERE adminMyID = '" + myID + "' LIMIT 1";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);

			this.results = ps.executeQuery();
			this.results.next();
			
			adminUser.setAdminID(results.getInt("adminID"));
			adminUser.setFname(results.getString("fname"));
			adminUser.setLname(results.getString("lname"));
			adminUser.setRole(results.getString("role"));
			adminUser.setAdminStatus(results.getInt("adminStatus"));
			adminUser.setCantBeDeleted(results.getInt("cantBeDeleted"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: get admin info method. Query = " + query);
		}
		
		
		return adminUser;
		
	}
	
}