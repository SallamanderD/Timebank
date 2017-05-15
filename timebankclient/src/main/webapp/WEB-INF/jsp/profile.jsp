<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>${user.username}</title>
</head>
<body style="background-color: white">
<jsp:include page="/navbar"></jsp:include>
<div class="container" style="margin-top: 5%">
    <h2>Личные данные:</h2>
    <hr>
    <div class="row">
        <div class="form-group col-md-4">
            <p class="lead">Логин: </p>
            <p class="lead">ФИО: </p>
            <p class="lead">Дата рождения: </p>
            <p class="lead">Дата регистрации: </p>
            <p class="lead">Баланс: </p>
        </div>
        <div class="form-group">
            <p class="lead">
                <mark>${user.username}</mark>
            </p>
            <p class="lead">
                <mark>${user.fio}</mark>
            </p>
            <p class="lead">
                <mark>${user.bornDate}</mark>
            </p>
            <p class="lead">
                <mark>${user.registerDate}</mark>
            </p>
            <p class="lead">
                <mark>${user.balance}</mark>
            </p>
        </div>
        <a href="/changeUser">
            <button type="submit" class="btn btn-success">Изменить данные</button>
        </a>
        <a href="/changePassword">
            <button type="submit" class="btn btn-success">Изменить пароль</button>
        </a>
        <a href="/chooseTags">
            <button type="submit" class="btn btn-success">Изменить теги</button>
        </a>
        <c:if test="${user.role.id == 1}"><a href="/adminPanel">
            <button type="submit" class="btn buttonSnipp">Адм. панель</button>
        </a></c:if>
        <hr>
        <h2>Теги на которые вы подписаны: </h2>
        <c:choose>
            <c:when test="${not empty user.preferTags}">
                <c:forEach var="listValue" items="${user.preferTags}" varStatus="loop">
                    <label class="btn btn-info">${listValue.name}</label>
                </c:forEach>
            </c:when>
            <c:otherwise>
                Вы не подписались ни на один тег. <a href="/chooseTags">Хотите сделать это сейчас?</a>
            </c:otherwise>
        </c:choose>
        <hr>
        <jsp:include page="/profileorder"></jsp:include>
    </div>
</div>
</body>
</html>