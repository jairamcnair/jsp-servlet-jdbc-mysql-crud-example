<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%> <!--  added isErrorPage="true", and exception underline went away -->
    
<!DOCTYPE html PUBLIC> <!--  added PUBLIC -->
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	<center>
		<h1> Error </h1>
		<h2>
			<%=exception.getMessage() %>
		</h2>
	</center>

</body>
</html>