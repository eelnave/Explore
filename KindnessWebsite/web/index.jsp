<%--
  Created by IntelliJ IDEA.
  User: Jordan
  Date: 2/24/2018
  Time: 7:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%--<form action="Login" method="post">--%>
<%--Enter username: <input type="text" name="user"><br>--%>
<%--Enter password: <input type="password" name="pass"><br>--%>
<%--<input type="submit" value="login">--%>
<%--</form>--%>
<h1>The Kindness App</h1>

<form method="post" action="http://localhost:8080/kindness">
    <b>Send Kindness Act</b><br>
    <label id="longitude">Longitude:</label><input type="text" name="longitude"><br>
    <label id="latitude">Latitude:</label><input type="text" name="latitude"><br>
    <label id="category">Category:</label><input type="text" name="category"><br>
    <label id="date">Date</label><input type="text" name="date"><br>
    <input type="submit" value="Send">
</form>

<hr>
<form method="get" action="http://localhost:8080/world">
    <input type="submit" value="World">
</form>
</body>
</html>
