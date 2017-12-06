<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="login">
        <link href="https://necolas.github.io/normalize.css/7.0.0/normalize.css" rel="stylesheet" type="text/css">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="css/general.css" rel="stylesheet" type="text/css">
        <link href="css/login.css" rel="stylesheet" type="text/css">
        <title>Login</title>
    </head>
    <body>
        <div>${message}</div>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h1 class="title">Sign Up</h1>
                </div>
                <form class="authForm clearfix" action="Authenticate" method="POST">
                    <input type="hidden" value="registration">
                    <div class="form-group">
                        <label for="Nickname">Nickname</label>
                        <input name="user" type="text" class="form-control" id="Nickname" placeholder="Nickname">
                    </div>
                    <div class="form-group">
                        <label for="Password">Password</label>
                        <input name="password" type="password" class="form-control" id="Password" placeholder="Password">
                    </div>
                    <a href="/Login" class="btn btn-default">SignIn</a>
                    <button type="submit" class="btn btn-primary pull-right">Sign Up</button>
                </form>
            </div>
        </div>
    </body>
</html>