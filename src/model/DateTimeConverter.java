/**
 *
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Brian Olaogun
 * This class is used to convert/parse/transform a date or time from a datetime variable or date variable.
 *
 */
public class DateTimeConverter {
	// Fields
	private String datetime;
	
	// Constructors 
	public DateTimeConverter(){
		this.datetime = null;
	}

	public DateTimeConverter(String datetime){
		this.datetime = datetime.trim(); // removes leading and trailing whitespace
	}

	// Accessors and Mutators for Fields
	
	public String getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime.trim(); // removes leading or trailing whitespace
	}
	
	
	

// The methods below convert and format mysql date(time) for display in Java & HTML	
	
	
	/**
	 * 
	 * @return converted date from mySQL format to "Month day, year" format.  Ex. January 23, 2015.
	 */
	public String convertDateLong(){
		
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert date to long format.**";
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return converted date from mySQL format to "Month day, year" format.  Ex. January 23, 2015.
	 */
	public String convertDateLong(String date){
		
		this.datetime = date.trim(); // removes leading and trailing whitespace
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert date to long format.**";
		}
	}
	
	/**
	 * 
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDateLong(){
	
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format 
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	
	/**
	 * 
	 * @param datetime 
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDateLong(String datetime){
		
		this.datetime = datetime.trim(); // removes leading and trailing whitespace
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("MMMM dd, yyyy"); // format date to ex. January 12, 2015
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	public String parseDate(){
		
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format 
			SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd"); 
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	
	/**
	 * 
	 * @param datetime 
	 * @return parsed Date from datetime in Month date, year format.
	 */
	public String parseDate(String datetime){
		
		this.datetime = datetime.trim(); // removes leading and trailing whitespace
		try{
			SimpleDateFormat unformatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String datetime to Simple Date Format
			SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd"); 
			Date parsedDate = unformatted.parse(this.datetime); // parse the date from the datetime
			return formatted.format(parsedDate); // return parsed date in String format
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to parse date.**";
		}
	}
	
	/**
	 * 
	 * @return String: parsed time from datetime. Converted time from 24-hour format to 12-hour format.
	 */
	public String parsedTimeTo12(){
		
		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String time to 24-hour DateTime
	        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
	        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
	}
	
	/**
	 * 
	 * @param time
	 * @return String: parsed time from datetime. Converted time from 24-hour format to 12-hour format.
	 */
	public String parsedTimeTo12(String datetime){
		
		this.datetime = datetime.trim(); // removes any leading or trailing whitespace
		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // used to convert String time to 24-hour DateTeime
	        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
	        return _12HourSDF.format(_24HourDt); // returns time in 12-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
		
	}
	
	public String parsedTimeTo24(){
		
		this.datetime = datetime.trim(); // removes any leading or trailing whitespace
		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // format of datetime being parsed
	        SimpleDateFormat _24HourSDF2 = new SimpleDateFormat("HH:mm:ss"); // format of parsed time
	        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
	        return _24HourSDF2.format(_24HourDt); // returns time in 24-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
		
	}
	
	public String parsedTimeTo24(String datetime){
		
		this.datetime = datetime.trim(); // removes any leading or trailing whitespace
		try {
			SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // format of datetime being parsed
	        SimpleDateFormat _24HourSDF2 = new SimpleDateFormat("HH:mm:ss"); // format of parsed time
	        Date _24HourDt = _24HourSDF.parse(this.datetime); // parse time from DateTime
	        return _24HourSDF2.format(_24HourDt); // returns time in 24-hour format
        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert time.**";
		}
		
	}
	/**
	 * 
	 * @return date in mySQL format from MM/dd/yyyy format.
	 */
	public String slashDateConvert (){
		
		try {
			SimpleDateFormat slashedFormat = new SimpleDateFormat("MM/dd/yyyy"); // used to convert String time to 24-hour DateTeime
	        SimpleDateFormat dashedFormat = new SimpleDateFormat("yyyy-MM-dd"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date d = slashedFormat.parse(this.datetime); // changes from string to date object
	        return dashedFormat.format(d); // returns date in SQL format
	        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert slashed date format to SQL format.**";
		}
	}
	
	/**
	 * 
	 * @param date in MM/dd/yyyy format
	 * @return date in mySQL format from MM/dd/yyyy format.
	 */
	public String slashDateConvert (String date){
		
		this.datetime = date.trim(); // removes leading and trailing whitespace
		try {
			SimpleDateFormat slashedFormat = new SimpleDateFormat("MM/dd/yyyy"); // used to convert String time to 24-hour DateTeime
	        SimpleDateFormat dashedFormat = new SimpleDateFormat("yyyy-MM-dd"); // used to convert 24-hour DateTime to 12-hour DateTime
	        Date d = slashedFormat.parse(this.datetime); // changes from string to date object
	        return dashedFormat.format(d); // returns date in SQL format
	        
		} catch (ParseException e) {
			e.printStackTrace();
			return "**Error, unable to convert slashed date format to SQL format.**";
		}
	}
	
	/**
	 * 
	 * @return current datetime in mySQL Format
	 */
	public String datetimeStamp(){
		
		Date stamp = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //MySQL format
		String currentDateTime = sdf.format(stamp);
		return currentDateTime;
	}
	/**
	 * 
	 * @param stringStartDate
	 * @param stringEndDate
	 * @return an arraylist of all dates in range of start date and end date inclusive.
	 */
	public List<String> dateRangeList(String stringStartDate, String stringEndDate){
		// convert string date to date object
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> dates = new ArrayList<String>();
		
		Date startdate;
		Date enddate;
		
		try {
			startdate = format.parse(stringStartDate);
			enddate = format.parse(stringEndDate);
			
			
		    Calendar calendar = new GregorianCalendar();
		    calendar.setTime(startdate);

		    while (calendar.getTime().getTime() <= enddate.getTime())
		    {
		        Date result = calendar.getTime();
		        String sqlFormattedResult = sqlFormat.format(result); 
		        dates.add(sqlFormattedResult);
		        calendar.add(Calendar.DATE, 1);
		    }
		    return dates;
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("DateTimeConverter.dateRangeList - Error printing date range: " + dates);
		}
		return null;
	}
	/**
	 * 
	 * @param stringStartTime
	 * @param stringEndTime
	 * @param hourIncrement
	 * @return
	 */
	public List<String> timeRangeList (String stringStartTime, String stringEndTime, String hourIncrement){
		TimeConverter tc = new TimeConverter();
		
		// if time is not in 24-hour format
		stringStartTime = tc.convertTimeTo24(stringStartTime);
		stringEndTime = tc.convertTimeTo24(stringEndTime);
		
		
		return null;
	}
	
	/**
	 * 
	 * @param startTime
	 * @param hourIncrement
	 * @return endTime
	 * This method is used to add an hour increment to a starting time probably in the most complicated way.
	 */
	public static String addTime(String reserveStartTime, int hourIncrement){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		
		try {
			// convert start time to date.  Convert increment to integer
			Date startTime = df.parse(reserveStartTime);
			
			// convert hour increment to seconds and add it to the start time
			Date add = new Date(startTime.getTime() + hourIncrement *(3600*1000));
			
			// format to get time only.  The result is the endTime
			// if endTime is 00:00:00, change end time to 23:59:59.
			String endTime = df.format(add);
			if(endTime.equals("0:00") || endTime.equals("00:00") || endTime.equals("00:00:00") || endTime.equals("0:00:00")){
				endTime = "23:59:59";
			}
			return endTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
// Main method used for testing. Will output to console.
	
	public static void main (String [] args){
		System.out.println("*******DateTimeConverter.java*******");
		System.out.println();
		DateTimeConverter dtc = new DateTimeConverter();
		dtc.setDatetime("2015-09-22 20:00:00");
		System.out.println("Print datetime: " + dtc.getDatetime());
		System.out.println();
		
		System.out.println("Parse time 12 hour: " + dtc.parsedTimeTo12());
		System.out.println("Parse time 24 hour: " + dtc.parsedTimeTo24());
		System.out.println("Parse date: " + dtc.parseDate());
		System.out.println("Current Datetime: " + dtc.datetimeStamp());
		
		System.out.println("TESTING ADD TIME METHOD: " + DateTimeConverter.addTime("23:00:00", 1));
	
		List<String> dates = dtc.dateRangeList("02/27/2016", "03/16/2016");
		for(int i=0; i<dates.size(); i++){
		    String date = dates.get(i);
		    System.out.println(" Date is ..." + date);
		}
	}
}
