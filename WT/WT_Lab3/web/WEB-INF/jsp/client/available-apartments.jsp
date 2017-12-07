<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Доступные номера"/>
        <c:set var="signout" value="Выход"/>
        <c:set var="book" value="Забронировать номер"/>
        <c:set var="booked" value="Показать забронированные номера"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Available apartments"/>
        <c:set var="booked" value="Show booked apartments"/>
        <c:set var="signout" value="Sign Out"/>
        <c:set var="book" value="Book apartment"/>
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
            <h3>${title}</h3>
        </div>
        <div class="panel-content">
            <div class="btn-group" role="group" style="margin-bottom: 10px;">
                <a href="/?page=available-apartments" class="btn btn-default">${book}</a>
                <a href="/?page=booked-apartments" class="btn btn-default">${booked}</a>
                <a href="/?page=sign-out" class="btn btn-default">${signout}</a>
            </div>
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th>hotel</th>
                    <th>room</th>
                    <th>action</th>
                </tr>
                </thead>
                <c:forEach var="apartment" items="${requestScope.apartments}">
                    <tr>
                        <td width="30%">${apartment.hotel}</td>
                        <td width="30%">${apartment.room}</td>
                        <td width="40%">
                            <form action="/" method="post" style="margin-bottom: 0;">
                                <input type="hidden" name="action" value="book-apartment" />
                                <input type="hidden" name="id" value="${apartment.id}"/>
                                <input type="submit" value="${book}" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>