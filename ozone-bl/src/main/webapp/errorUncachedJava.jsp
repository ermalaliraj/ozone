<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>OZone Admin Tool | Internal Error</title>
</head>

<body>
	<div class="contenuto">
		<h1>Caught Error in JSP Page</h1>
		<p>&raquo; <a href="javascript:history.back()">Back</a></p>
	</div>
	
	Error: <b><%= exception.getMessage() %></b><br><br>
	
	Stack:
	<pre>
	<% exception.printStackTrace(new PrintWriter(out)); %>
	</pre>
	
	
	<p><b><i>Please check log files for more info.</i></b></p>
</body>

</html>