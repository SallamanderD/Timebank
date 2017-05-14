<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Регистрация</title>
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
    <c:if test="${not empty errors}">
        <div class="notice notice-warning">
        <c:forEach items="${errors}" var="error">
            <p>${error}</p>
        </c:forEach>
        </div>
    </c:if>
    <form action="/register" method="post">
        <div class="col-sm-5">
            <div class="form-group input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="username" type="text" class="form-control" value="${user.username}" name="username" placeholder="Логин">
            </div>
            <div class="form-group input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-eye-close"></i></span>
                <input id="fio" type="text" class="form-control" value="${user.fio}" name="fio" placeholder="ФИО">
            </div>
            <div class="form-group input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="password" type="password" class="form-control" name="password" placeholder="Пароль">
            </div>
            <div class="form-group input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="repassword" type="password" class="form-control" name="repassword"
                       placeholder="Повторите пароль">
            </div>
            <div class="form-group input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                <input type="date" name="bornDate" value="${user.bornDate}" class="form-control">
            </div>
            <div class="form-group input-group">
                <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Зарегестрироваться</button><a href="/login">  Уже есть аккаунт?</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>