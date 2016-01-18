/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		public RoomsSelectQuery(String dbName, String user, String pwd) {
			String url = "jdbc:mysql://localhost:3306/" + dbName;
	
			// set up the driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				this.connection = DriverManager.getConnection(url, user, pwd);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		public void doRoomRead(String building, String floor){
			//String query = "SELECT * FROM tomcatdb.Rooms WHERE roomStatus = 1";
			String query = "SELECT roomNumber FROM tomcatdb.Rooms, tomcatdb.Building WHERE tomcatdb.Rooms.Building_buildingID = tomcatdb.Building.buildingID AND tomcatdb.Building.buildingName = '" + building + "'" + " AND tomcatdb.Rooms.roomStatus = 1 AND tomcatdb.Rooms.roomFloor = '" + floor + "'" + " ORDER BY roomNumber";

			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				this.results = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
			}
		}
		
		
		
		public String getRoomsTable(){
			// create the times to display in a table.  
			String[] timeBlock = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", 
					"11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			
			// Create an HTML table
			String table = "";
			//table += "<table border=1>";
			
			table += "<div id='tabs'>";
			table += "<ul>";
			
			int h = 0;
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
			int j = 0;
			try {
				while(this.results.next()){
					// get the room number and reserved rooms from the query results
					Rooms room = new Rooms();
					room.setRoomNumber(this.results.getInt("roomNumber"));
					
					// TODO get results for reservation
					
					
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
						table += "<td>";
						table += tc.convertTimeTo12(timeBlock[i]);
						table += "</td>";
					}
					table += "</tr>";
					table += "<tr>";
					for(int i = 12; i < timeBlock.length; i++){
						table += "<td>";
						table += tc.convertTimeTo12(timeBlock[i]);
						table += "</td>";
					}
					
					table += "</tr>";
					table += "</tbody>";
					
					table += "</table>";
					
					table += "</div>";
					j++;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//table += "</table>";
			table += "</div>";
			return table;
		}
}