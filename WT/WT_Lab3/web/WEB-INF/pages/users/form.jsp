<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>list</title>
</head>
<body>
	<form action="UserSave" method="post">
		<input type="hidden" name="oldUser" value="${user.id}"> 
		User:<input	name="user" value="${user.id}"><br> 
		Password:<input name="password" type="password" value="${user.password}"><br>
		Role:
		<select name="role">
			<option>ADMIN</option>
			<option>USER</option>
		</select> 
		<input type="submit" value="SAVE">
	</form>
	<c:if test="${user.id ne null}">
		<form action="UserDelete">
			<input type="hidden" name="oldUser" value="${user.id}"><br>
			<input type="submit" value="DELETE">
		</form>
	</c:if>
	<a href="UserList">back</a><br>
	<a href="Welcome">Home</a>
</body>
</html>