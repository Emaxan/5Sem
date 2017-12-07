<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Добавить номер"/>
        <c:set var="signout" value="Выход"/>
        <c:set var="hotel" value="Отель"/>
        <c:set var="room" value="Номер"/>
        <c:set var="add" value="Добавить"/>
        <c:set var="signin" value="У меня есть аккаунт"/>
        <c:set var="add1" value="Добавить номер"/>
        <c:set var="edit" value="Редактировать список номеров"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Add apartment"/>
        <c:set var="signout" value="Sign Out"/>
        <c:set var="hotel" value="Hotel"/>
        <c:set var="room" value="Room"/>
        <c:set var="add" value="Add"/>
        <c:set var="signin" value="I have account"/>
        <c:set var="add1" value="Add apartment"/>
        <c:set var="edit" value="Edit apartments list"/>
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
                <a href="/?page=add-apartment" class="btn btn-default">${add1}</a>
                <a href="/?page=apartments-list" class="btn btn-default">${edit}</a>
                <a href="/?page=sign-out" class="btn btn-default">${signout}</a>
            </div>
            <form action="/" method="post">
                <input type="hidden" name="action" value="add-apartment">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" id="sizing-addon1">H</span>
                    <input name="hotel" type="text" class="form-control" placeholder="${hotel}" aria-describedby="sizing-addon1" minlength="1" maxlength="50" required>
                </div>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" id="sizing-addon2">R</span>
                    <input name="room" type="text" class="form-control" placeholder="${room}" aria-describedby="sizing-addon2" minlength="1" maxlength="10" required>
                </div>
                <input class="btn btn-default" type="submit" value="${add}" />
            </form>
        </div>
    </div>
</div>
</body>
</html>
