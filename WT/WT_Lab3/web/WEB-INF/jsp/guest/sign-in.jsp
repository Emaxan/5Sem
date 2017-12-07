<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${requestScope.lang == 'rus'}">
        <c:set var="title" value="Авторизация"/>
        <c:set var="email" value="email"/>
        <c:set var="password" value="пароль"/>
        <c:set var="signin" value="Войти"/>
        <c:set var="signup" value="Нет аккаунта?"/>
        <c:set var="language" value="Выберите язык"/>
        <c:set var="select" value="Выбрать"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Sign In"/>
        <c:set var="email" value="email"/>
        <c:set var="password" value="password"/>
        <c:set var="signin" value="Sign In"/>
        <c:set var="signup" value="Don't have account?"/>
        <c:set var="language" value="Select language"/>
        <c:set var="select" value="Select"/>
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
                <h1 align="center">SignIn</h1>
            </div>
            <div class="panel-content">
                <form class="clearfix" action="/" method="post">
                    <input type="hidden" name="action" value="sign-in">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="sizing-addon1">@</span>
                        <input name="email" type="email" class="form-control" placeholder="${email}" aria-describedby="sizing-addon1" required>
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="sizing-addon2">   *   </span>
                        <input name="password" type="password" class="form-control" placeholder="${password}" pattern="[\w]{4,}" aria-describedby="sizing-addon2" required>
                    </div>
                    <input class="btn btn-default pull-right" type="submit" value="${signin}" />
                    <a class="btn btn-default pull-left" href="/?page=sign-up">${signup}</a>
                </form>
                <form action="/" method="post">
                    <input type="hidden" name="action" value="set-lang">
                    <p>${language}</p>
                    <div>
                        <input type="radio" id="lang_eng" name="language" value="eng" checked>
                        <label for="lang_eng">English</label>
                        <input type="radio" id="lang_rus" name="language" value="rus">
                        <label for="lang_rus">Русский</label>
                    </div>
                    <p><input type="submit" value="${select}" /></p>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
