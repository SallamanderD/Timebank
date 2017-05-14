<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/navbar.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
</head>
<body>
<nav class="navbar navbar-findcond navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/feed">Timebank</a>
        </div>
        <div class="collapse navbar-collapse" id="navbar">
            <ul class="nav navbar-nav navbar-left">
                <li class="active"><a href="/feed">Лента</a></li>
                <li class="active"><a href="/orders">Заказы</a></li>
                <li class="active"><a href="/placeorder">Создать заказ</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty user}">
                    <li class="active"><a href="/profile">${user.username}</a></li>
                    <li class="active"><a href="/logout">Выход</a></li>
                </c:if>
                <c:if test="${empty user}">
                    <li class="active"><a href="/login">Войти</a></li>
                    <li class="active"><a href="/register">Нет аккаунта?</a></li>
                </c:if>
            </ul>
            <form class="navbar-form navbar-right search-form" role="search">
                <input type="text" class="form-control" placeholder="Search" />
            </form>
        </div>
    </div>
</nav>
</body>
</html>