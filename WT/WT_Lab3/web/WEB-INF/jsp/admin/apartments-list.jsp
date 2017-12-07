<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Список номеров"/>
        <c:set var="signout" value="Выход"/>
        <c:set var="delete" value="Удалить номер"/>
        <c:set var="booked" value="Забронирован"/>
        <c:set var="available" value="Доступен"/>
        <c:set var="add" value="Добавить номер"/>
        <c:set var="edit" value="Редактировать список номеров"/>
        <c:set var="hotel" value="Отель"/>
        <c:set var="room" value="Номер"/>
        <c:set var="action" value="Действие"/>
        <c:set var="state" value="Состояние"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Apartments list"/>
        <c:set var="signout" value="Sign Out"/>
        <c:set var="delete" value="Delete apartment"/>
        <c:set var="booked" value="Booked"/>
        <c:set var="available" value="Available"/>
        <c:set var="add" value="Add apartment"/>
        <c:set var="edit" value="Edit apartments list"/>
        <c:set var="hotel" value="Hotel"/>
        <c:set var="room" value="Room"/>
        <c:set var="state" value="State"/>
        <c:set var="action" value="Action"/>
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
                <a href="/?page=add-apartment" class="btn btn-default">${add}</a>
                <a href="/?page=apartments-list" class="btn btn-default">${edit}</a>
                <a href="/?page=sign-out" class="btn btn-default">${signout}</a>
            </div>
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>${hotel}</th>
                        <th>${room}</th>
                        <th>${state}</th>
                        <th>${action}</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="apartment" items="${requestScope.apartments}">
                        <tr>
                            <td width="60%">${apartment.hotel}</td>
                            <td width="15%">${apartment.room}</td>
                            <td width="20%">
                                <c:choose>
                                    <c:when test="${apartment.guestId == 0}">
                                        ${available}
                                    </c:when>
                                    <c:otherwise>
                                        ${booked}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td align="right">
                                <form action="/" method="post" style="margin-bottom: 0;">
                                    <input type="hidden" name="action" value="delete-apartment" />
                                    <input type="hidden" name="id" value="${apartment.id}"/>
                                    <input type="submit" value="${delete}" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
