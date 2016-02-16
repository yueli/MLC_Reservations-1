/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DbConnect;
import model.Reservation;

/**
 * @author Brian Olaogun
 * Helper for the Student side of the website.  This is for Student Browse.
 *
 */
public class ReservationSelectQuery {
	
	// initialize fields
	private Connection connection;
	private ResultSet results;
			
	/**
	 * 
	 */
	public ReservationSelectQuery() {

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
	
	public void doReservationRead(String currentDate, String time, int roomNumber){
		String query = "SELECT Reservations.reserveID FROM tomcatdb.Reservations, tomcatdb.Rooms WHERE Reservations.reserveStartDate = '" + currentDate + "'" + "AND ((Reservations.reserveStartTime = '" + time + "') OR ('" + time + "' BETWEEN reserveStartTime AND reserveEndTime)) AND Rooms.roomID = Reservations.Rooms_roomID and Rooms.roomNumber = " + roomNumber + " AND tomcatdb.Reservations.free = 'N'";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
		} 
	}
	
	public String doReservationResults(){
		String results = "";
		try {
			while(this.results.next()){
				Reservation reservation = new Reservation();
				reservation.setReserveID(this.results.getInt("reserveID"));
				results += reservation.getReserveID();
			} this.results.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return results;
	}

}
