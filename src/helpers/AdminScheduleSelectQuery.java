/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Building;
import model.DateTimeConverter;
import model.DbConnect;
import model.Schedule;
import model.TimeConverter;

/**
 * @author Brian Olaogun
 *
 */
public class AdminScheduleSelectQuery {
	private Connection connection;
	private ResultSet results;
	/**
	 * 
	 */
	public AdminScheduleSelectQuery() {
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
	
	public void doRead(String buildingID){
		String query = "SELECT tomcatdb.Building.buildingName, "
				+ "tomcatdb.Building.buildingID, "
				+ "tomcatdb.Schedule.startDate, "
				+ "tomcatdb.Schedule.endDate, "
				+ "tomcatdb.Schedule.startTime, "
				+ "tomcatdb.Schedule.endTime, "
				+ "tomcatdb.Schedule.summary, "
				+ "tomcatdb.Schedule.createdBy "
				+ "FROM tomcatdb.Building, tomcatdb.Schedule "
				+ "WHERE tomcatdb.Schedule.Building_buildingID = tomcatdb.Building.buildingID "
				+ "AND tomcatdb.Schedule.Building_buildingID ='" + buildingID + "'";
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in AdminScheduleSelectQuery.java: doRead method. Please check connection or SQL statement: " + query);
		} 
	}
	
	public String listSchedule(){
		String table = "";
		try {
			table += "<table id='' class='display'>";
			table += "<thead>";
			table += "<tr>";
			table += "<th></th>";
			table += "<th>Building </th>";
			table += "<th>Date </th>";
			table += "<th>Start Time</th>";
			table += "<th>End Time</th>";
			table += "<th>Summary</th>";
			table += "<th>Created By</th>";
			table += "</tr>";
			table += "</thead>";
			table += "<tbody>";
			int j = 1; // for counter displayed in HTML table
			while (this.results.next()){
				// place query results in schedule and building objects
				Schedule schedule = new Schedule();
				schedule.setBuildingID(this.results.getInt("buildingID"));
				schedule.setStartDate(this.results.getString("startDate"));
				schedule.setEndDate(this.results.getString("endDate"));
				schedule.setStartTime(this.results.getString("startTime"));
				schedule.setEndTime(this.results.getString("endTime"));
				schedule.setSummary(this.results.getString("summary"));
				schedule.setCreatedBy(this.results.getString("createdBy"));
				
				Building building = new Building();
				building.setBuildingName(this.results.getString("buildingName"));
				
				// used for displaying date and time in a user friendly format
				DateTimeConverter dtc = new DateTimeConverter();
				TimeConverter tc = new TimeConverter();
				
				table += "<tr>";
				table += "<td>" + j + "</td>";
				table += "<td data-search='" + building.getBuildingName() + "'>" + building.getBuildingName() + "</td>";
				table += "<td data-search='" + schedule.getStartDate()+ "'>" + dtc.convertDateLong(schedule.getStartDate()) + "</td>";
				table += "<td data-order='" + schedule.getStartTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(schedule.getStartTime()) + "</td>";
				table += "<td data-order='" + schedule.getEndTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(schedule.getEndTime()) + "</td>";
				table += "<td data-search='" + schedule.getSummary() + "'>" + schedule.getSummary() + "</td>";
				table += "<td data-search='" + schedule.getCreatedBy() + "'>" + schedule.getCreatedBy() + "</td>";
				table += "</tr>";
				j++;
			}
			table += "</tbody>";
			table += "</table>";
		} catch (SQLException e){
			e.printStackTrace();
		}
		return table;
	}
	

}
