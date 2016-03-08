<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Reservations</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		
		<!-- Meta -->
        
        <meta name="description" content="">
        <meta name="author" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
       
        <!-- Template CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="user/browse.css">
 		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
        
		<script> 
			// used to load header and footer html
			$(function() {
				$("#header").load("user/userheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// jQuery for datepicker plugin
			$(function() {
			    $( "#datepicker" ).datepicker({ minDate: -1, maxDate: +14, dateFormat: "yy-mm-dd" });
			 });
			// jQuery for Datatable plugin for pagination
			$(document).ready( function () {
				$('table.display').DataTable();
  
			});
		</script> 
	</head>
	<body>
		<div id="header"></div>
			<br><br><br><br><br>
			<div id="search-container">
				<form name="adminViewForm" action="view-reservations?building?date" method="post">
					<p>Building ${buildings}<input name="enterBuilding" type="submit" value="Enter"></p>
				</form>
				<form name="adminViewForm2" action="view-reservations?building?date" method="post">
					<p>Date <input type="text" name="datepicker" id="datepicker"><input name="dateSort" type="submit" value="Enter"></p>
				</form>
			</div>
			<div>
			<h2>Admin Reservations for ${currentDateLong}</h2>
			<p>${adminReservations}</p>
			</div>
			<h2>Student Reservations for ${currentDateLong}</h2>
			<p>${userReservations}</p>
			<br><br><br><br>
		
		<div id="footer"></div>
	</body>
</html>