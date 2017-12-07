<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Клиент"/>
        <c:set var="signout" value="Выход"/>
        <c:set var="welcome" value="Добро пожаловать, ${requestScope.email}!"/>
        <c:set var="book" value="Забронировать номер"/>
        <c:set var="booked" value="Показать забронированные номера"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Client"/>
        <c:set var="signout" value="Sign Out"/>
        <c:set var="welcome" value="Welcome, ${requestScope.email}!"/>
        <c:set var="book" value="Book apartment"/>
        <c:set var="booked" value="Show booked apartments"/>
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
            <div class="panel-heading">
                <h3>${welcome}</h3>
            </div>
            <div class="panel-content">
                <div class="btn-group" role="group">
                    <a href="/?page=available-apartments" class="btn btn-default">${book}</a>
                    <a href="/?page=booked-apartments" class="btn btn-default">${booked}</a>
                    <a href="/?page=sign-out" class="btn btn-default">${signout}</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
