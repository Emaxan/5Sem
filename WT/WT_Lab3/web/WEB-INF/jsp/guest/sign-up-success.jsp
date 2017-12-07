<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Регистрация"/>
        <c:set var="message" value="Вы успешно зарегистрированы"/>
        <c:set var="signin" value="Авторизироваться"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Sign Up"/>
        <c:set var="message" value="Your sign up was successful"/>
        <c:set var="signin" value="Sign In"/>
    </c:otherwise>
</c:choose>

<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/general.css">
    <title>${title}</title>
</head>
<body>
<div class="container">
    <div class="panel panel-default" align="center">
        <div class="panel-content">
            <h3>${message}</h3>
            <a class="btn btn-default" href="/?page=sign-in">${signin}</a>
        </div>
    </div>
</div>
</body>
</html>