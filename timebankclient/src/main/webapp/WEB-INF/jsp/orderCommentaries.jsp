<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>

</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${not empty commentaries}">
            <c:forEach var="listValue" items="${commentaries}" varStatus="loop">
                <div class="col-md-12">
                    <h3><a href="/user/${listValue.author.username}">${listValue.author.username}</a></h3>
                    <p>${listValue.text}</p>
                    <div>
                        <span class="badge"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span> Размещен: ${listValue.createDate}</span>
                    </div>
                    <div class="pull-right">
                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                        <a href="#">Пожаловаться?</a>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            Oops! Empty list.
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>