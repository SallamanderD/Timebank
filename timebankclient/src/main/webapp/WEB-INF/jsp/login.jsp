<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Авторизация</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/navbar.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
    <link href="${pageContext.request.contextPath}/css/notification.css" rel="stylesheet">
</head>
<body style="background-color: white">
<jsp:include page="/navbar"></jsp:include>
<div class="container" style="margin-top: 5%">
    <c:if test="${not empty error}">
        <div class="notice notice-warning">
            <strong>Внимание: </strong> ${error}
        </div>
    </c:if>
    <form action="/login" method="post">
        <div class="col-sm-5">
            <div class="form-group input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="username" type="text" class="form-control" name="username" placeholder="Логин">
            </div>
            <div class="form-group input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="password" type="password" class="form-control" name="password" placeholder="Пароль">
            </div>
            <div class="form-group input-group">
                <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Войти</button><a href="/register">  Нет аккаунта?</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>