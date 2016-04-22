<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WebChat Index</title>
</head>
<body>
	<form action="login.htm" method="POST">
		<table>
			<tr>
				<td>USERNAME</td>
				<td><input type="text" name="uname" /></td>
			</tr>
			<tr>
				<td>PASSWORD</td>
				<td><input type="password" name="pword" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="SUBMIT" /></td>
			</tr>
		</table>
	</form>
	<form action="addUser.htm" method="POST">
		<table>
			<tr>
				<td>USERNAME</td>
				<td><input type="text" name="uname" /></td>
			</tr>
			<tr>
				<td>PASSWORD</td>
				<td><input type="password" name="pword" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="REGISTER ME" /></td>
			</tr>
		</table>
	</form>
</body>
</html>