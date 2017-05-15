<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Главная</title>
</head>
<body style="background-color: white">
<jsp:include page="/navbar"></jsp:include>
<div class="container" style="margin-top: 5%">
    <c:choose>
    <c:when test="${not empty orders}">
    <div class="col-md-12">
        <c:forEach var="listValue" items="${orders}" varStatus="loop">

        <h1><a href="/order/${listValue.id}">${listValue.name}</a></h1>
        <p>${listValue.description}</p>
        <div>
                <span class="badge"><span class="glyphicon glyphicon-calendar"
                                          aria-hidden="true"></span> Размещен ${listValue.createDate}</span>
            <div class="pull-right">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                <a href="#">${listValue.author.username}</a>
                <span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
                    ${listValue.count}
                <span class="glyphicon glyphicon-tags" aria-hidden="true"></span>
                Теги:
                <c:forEach var="tag" items="${listValue.tags}"><span
                        class="label label-success">${tag.name}</span></c:forEach>
            </div>

            <hr>
            </c:forEach>
        </div>
        </c:when>
        <c:otherwise>
            Oops! Empty list.
        </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>