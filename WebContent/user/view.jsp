<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String table = (String) request.getAttribute("table");
	String message = (String) request.getAttribute("message");
%> 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="WEB-INF/global.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Reservations Home Page</title>
</head>
<body>

<h1>View Home Page</h1>
<h2>Reservations for: 
	${user.userFirstName} 
	${user.userLastName}
</h2>
	<%= message %>
	<%= table %>
	
	
</body>
</html>