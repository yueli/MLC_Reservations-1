/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DateTimeConverter;
import model.DbConnect;
import model.Rooms;
import model.TimeConverter;

/**
 * @author Brian Olaogun
 * Helper for the Student side of the website.
 *
 */
public class RoomsSelectQuery {
	// initialize fields
		private Connection connection;
		private ResultSet results;
		
		/**
		 * Default Constructor
		 */
		public RoomsSelectQuery() {
	
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
		
		
		public void doRoomRead(int building, String floor){
			//String query = "SELECT * FROM tomcatdb.Rooms WHERE roomStatus = 1";
			String query = "SELECT roomID, roomNumber FROM tomcatdb.Rooms, tomcatdb.Building WHERE tomcatdb.Rooms.Building_buildingID = "
					+ "tomcatdb.Building.buildingID AND tomcatdb.Building.buildingID = '" + building + "'" + " AND tomcatdb.Rooms.roomStatus = 1 "
							+ "AND tomcatdb.Rooms.roomFloor = '" + floor + "'" + " ORDER BY roomNumber";

			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				this.results = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement: " + query);
			} 
		}
		
		
		
		public String getRoomsTable(){
			// create the times to display in a table.  
			String[] timeBlock = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", 
					"11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			
			// Create an HTML table
			String table = "";
			
			table += "<div id='tabs'>";
			table += "<ul>";
			
			int h = 0; // used for jQuery tabs creation
			try {
				while(this.results.next()){
					Rooms rooms = new Rooms();
					rooms.setRoomNumber(this.results.getInt("roomNumber"));
					table += "<li><a href='#tabs-" + h + "'" + ">" + rooms.getRoomNumber() + "</a></li>";
					h++;
				}
				
				this.results.beforeFirst();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			table += "</ul>";
			// Class to convert 24 hour time to 12 hour
			TimeConverter tc = new TimeConverter();
			int j = 0; // used for jQuery tabs creation
			try {
				while(this.results.next()){
					// get the room number and reserved rooms from the query results
					Rooms room = new Rooms();
					room.setRoomID(this.results.getInt("roomID")); // TODO
					room.setRoomNumber(this.results.getInt("roomNumber"));
					
					// display results in a table
					table += "<div id='tabs-" + j + "'" + ">";
					table += "<table border=1>";
					table += "<tbody class='room'>";
					table += "<tr>";
					table += "<th id='header' COLSPAN=12 ALIGN=CENTER><h3>";
					table += room.getRoomNumber();
					table += "</h3></th>";
					table += "</tr>";
					table += "</tbody>";
					
					table += "<tbody class='subcategory'>";
					table += "<tr>";
					for(int i = 0; i < 12; i++){
						// get results for reservations
						ReservationSelectQuery rsq = new ReservationSelectQuery();
						
						// class used to get the current datetime and parse date
						DateTimeConverter dtc = new DateTimeConverter();
						
						// check to see if room is reserved at the current hour at the current date
						rsq.doReservationRead(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i], room.getRoomNumber());
						String reservation = rsq.doReservationResults();
						
						// used for hour comparison
						String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp());
						int currentHour = Integer.parseInt(currentTime.substring(0, Math.min(currentTime.length(), 2)));
						int reserveHour = Integer.parseInt(timeBlock[i].substring(0, Math.min(timeBlock[i].length(), 2)));
						System.out.println("PRINT of CURRENT HOUR: " + currentHour + " PRINT OF TIMEBLOCK HOUR: " + reserveHour);
						
						// if result set IS NOT empty, then there IS a reservation at that time
						if(!reservation.isEmpty()){
							table += "<td id='red'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// compare the current hour with the hour of the reservation
						// user can only make a reservation for current hour and beyond for the day
						} else if (reserveHour < currentHour){
							table += "<td id='gray'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// if result set IS empty, then there IS NOT a reservation at that time
						} else {
							table += "<td id='green'>";
							table += "<form name='fwdReserve' id='fwdReserve" + i + room.getRoomNumber() + "' action='BrowseReserve' method='post'>";
							table += "<input type='hidden' name='roomID' value='" + room.getRoomID() + "'>";
							table += "<input type='hidden' name='startTime' value='" + timeBlock[i] + "'>";
							table += "<input type='hidden' name='roomNumber' value='" + room.getRoomNumber() + "'>";
							table += "<input type='hidden' name='currentDate' value='" + dtc.parseDate(dtc.datetimeStamp()) + "'>";
							table += "<a href='javascript: submitform(" + i + ", " + room.getRoomNumber() + ")'>" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
							table += "</form> ";
							//table += "<a href=Browse_Reservation?startTime=" + timeBlock[i] + "&roomNumber=" + room.getRoomNumber() + "&currentDate=" + dtc.parseDate(dtc.datetimeStamp()) + " onclick='document.getElementById('reserve_submit').submit(); return false;'" + ">" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
						}

						table += "</td>";
					}
					table += "</tr>";
					table += "<tr>";
					for(int i = 12; i < timeBlock.length; i++){
						// get results for reservation
						ReservationSelectQuery rsq = new ReservationSelectQuery();
						
						// class used to get the current datetime and parse date
						DateTimeConverter dtc = new DateTimeConverter();
						
						// check to see if room is reserved at the current hour at the current date
						rsq.doReservationRead(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i], room.getRoomNumber());
						String reservation = rsq.doReservationResults();
						
						// used for hour comparison
						String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp());
						int currentHour = Integer.parseInt(currentTime.substring(0, Math.min(currentTime.length(), 2)));
						int reserveHour = Integer.parseInt(timeBlock[i].substring(0, Math.min(timeBlock[i].length(), 2)));
						//System.out.println("@PRINT of CURRENT HOUR: " + currentHour + " PRINT OF TIMEBLOCK HOUR: " + reserveHour);
						
						// if result set IS NOT empty, then there IS a reservation at that time
						if(!reservation.isEmpty()){
							table += "<td id='red'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// compare the current hour with the hour of the reservation
						// user can only make a reservation for current hour and beyond for the day
						}else if (reserveHour < currentHour){
							table += "<td id='gray'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// if result set IS empty, then there IS NOT a reservation at that time
						} else {
							table += "<td id='green'>";
							table += "<form name='fwdReserve' id='fwdReserve" + i + room.getRoomNumber() + "' action='BrowseReserve' method='post'>";
							table += "<input type='hidden' name='roomID' value='" + room.getRoomID() + "'>";
							table += "<input type='hidden' name='startTime' value='" + timeBlock[i] + "'>";
							table += "<input type='hidden' name='roomNumber' value='" + room.getRoomNumber() + "'>";
							table += "<input type='hidden' name='currentDate' value='" + dtc.parseDate(dtc.datetimeStamp()) + "'>";
							table += "<a href='javascript: submitform(" + i + ", " + room.getRoomNumber() + ")'>" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
							table += "</form> ";
							//table += "<a href=Browse_Reservation?startTime=" + timeBlock[i] + "&roomNumber=" + room.getRoomNumber() + "&currentDate=" + dtc.parseDate(dtc.datetimeStamp()) + ">" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
						}
						
					}
					table += "</tr>";
					table += "</tbody>";
					table += "</table>";
					// Key for rooms table
					table += "<br><br><br>";
					table += "<table>";
					table += "<tbody class='room'>";
					table += "<tr>";
					table += "<th id='header' COLSPAN=3 ALIGN=CENTER><h3>";
					table += "Key";
					table += "</h3></th>";
					table += "</tr>";
					table += "</tbody>";
					table += "<tbody class='subcategory'>";
					table += "<tr>";
					table += "<td id='gray'>" + "Time Unavailable" + "</td>";
					table += "<td id='red'>" + "Time Reserved" + "</td>";
					table += "<td id='green'>" + "Time Available" + "</td>";
					table += "</tr>";
					table += "</tbody>";
					table += "</table>";
					table += "</div>";
					j++; // used for jQuery tabs creation
				}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			table += "</div>";
			return table;
		}
}
