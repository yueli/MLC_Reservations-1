<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Buildings</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
		 <!-- Template CSS -->
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="user/browse.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
		<link rel="stylesheet" type="text/css" href="jquery/Responsive/css/responsive.dataTables.css">
		        
		<script> 
		
		
			function validateForm() {
				var start = document.forms["searchForm"]["beginDate"].value;
				var end = document.forms["searchForm"]["endDate"].value;
				
				var dStart = Date.parse(start);
				var dEnd = Date.parse(end);
				
				
			    if (dEnd<dStart) {
			        alert("End Date must be a later time");
			        return false;
			    }
		}
		
		
			$(function() {
				$("#header1").load("user/userheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// jQuery for Datatable plugin for pagination
			$(document).ready( function () {
				$('table.display').DataTable();
		
			});
		
		</script> 
		
		
		
		
		
	</head>
	<body>
		<div id="header1"></div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<div align="center">
	
		
		<p>${table}</p>
		</div>
		<div id="footer"></div>
	</body>
</html>
