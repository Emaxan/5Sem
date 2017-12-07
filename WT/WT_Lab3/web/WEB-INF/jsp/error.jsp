<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Ошибка"/>
        <c:set var="main" value="На главную"/>

        <c:choose>
            <c:when test="${requestScope.error == '0'}">
                <c:set var="error" value="Неверные данные"/>
            </c:when>
            <c:when test="${requestScope.error == '1'}">
                <c:set var="error" value="Не удалось установить соединение с базой данных"/>
            </c:when>
            <c:when test="${requestScope.error == '2'}">
                <c:set var="error" value="Не удалось выполнить запрос к базе данных данных"/>
            </c:when>
            <c:when test="${requestScope.error == '3'}">
                <c:set var="error" value="Пользователь с таким email уже существует"/>
            </c:when>
            <c:when test="${requestScope.error == '4'}">
                <c:set var="error" value="Пользователья с таким email не существует"/>
            </c:when>
            <c:when test="${requestScope.error == '5'}">
                <c:set var="error" value="Неверный пароль"/>
            </c:when>
            <c:when test="${requestScope.error == '6'}">
                <c:set var="error" value="Нет забронированных номеров"/>
            </c:when>
            <c:when test="${requestScope.error == '7'}">
                <c:set var="error" value="Нет доступных для бронирования номеров"/>
            </c:when>
            <c:when test="${requestScope.error == '8'}">
                <c:set var="error" value="Запрошенный номер не найден"/>
            </c:when>
            <c:when test="${requestScope.error == '9'}">
                <c:set var="error" value="Нет номеров"/>
            </c:when>
            <c:otherwise>
                <c:set var="error" value="Неизвестная ошибка"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Error"/>
        <c:set var="main" value="Main page"/>

        <c:choose>
            <c:when test="${requestScope.error == '0'}">
                <c:set var="error" value="Incorrect data"/>
            </c:when>
            <c:when test="${requestScope.error == '1'}">
                <c:set var="error" value="Unable to connect to database"/>
            </c:when>
            <c:when test="${requestScope.error == '2'}">
                <c:set var="error" value="Unable to execute query database"/>
            </c:when>
            <c:when test="${requestScope.error == '3'}">
                <c:set var="error" value="User with same email exists"/>
            </c:when>
            <c:when test="${requestScope.error == '4'}">
                <c:set var="error" value="No user with specified email"/>
            </c:when>
            <c:when test="${requestScope.error == '5'}">
                <c:set var="error" value="Incorrect password"/>
            </c:when>
            <c:when test="${requestScope.error == '6'}">
                <c:set var="error" value="No booked apartments"/>
            </c:when>
            <c:when test="${requestScope.error == '7'}">
                <c:set var="error" value="No available apartments"/>
            </c:when>
            <c:when test="${requestScope.error == '8'}">
                <c:set var="error" value="Requested apartment not found"/>
            </c:when>
            <c:when test="${requestScope.error == '9'}">
                <c:set var="error" value="No apartments"/>
            </c:when>
            <c:otherwise>
                <c:set var="error" value="${requestScope.error}"/>
            </c:otherwise>
        </c:choose>
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
                <h3>${error}</h3>
                <a class="btn btn-default" href="/">${main}</a>
            </div>
        </div>
    </div>
</body>
</html>
