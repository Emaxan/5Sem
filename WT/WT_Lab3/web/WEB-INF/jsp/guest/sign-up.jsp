<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Регистрация"/>
        <c:set var="email" value="email"/>
        <c:set var="password" value="пароль"/>
        <c:set var="signup" value="Зарегистрироваться"/>
        <c:set var="signin" value="У меня есть аккаунт"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Sign Up"/>
        <c:set var="email" value="email"/>
        <c:set var="password" value="password"/>
        <c:set var="signup" value="Sign Up"/>
        <c:set var="signin" value="I have account"/>
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
            <h1 align="center">Sign Up</h1>
        </div>
        <div class="panel-content">
            <form class="clearfix" action="/" method="post">
                <input type="hidden" name="action" value="sign-up">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" id="sizing-addon1">@</span>
                    <input name="email" type="email" class="form-control" placeholder="${email}" aria-describedby="sizing-addon1" required>
                </div>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" id="sizing-addon2">   *   </span>
                    <input name="password" type="password" class="form-control" placeholder="${password}" pattern="[\w]{4,}" aria-describedby="sizing-addon2" required>
                </div>
                <input class="btn btn-default pull-right" type="submit" value="${signup}" />
                <a class="btn btn-default pull-left" href="/?page=sign-in">${signin}</a>
            </form>

        </div>
    </div>
</div>
</body>
</html>
