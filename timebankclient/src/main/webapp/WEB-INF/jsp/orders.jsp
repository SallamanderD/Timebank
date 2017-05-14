<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Заказы</title>
    <link href="webjars/bootstrap/3.2.0/css/bootstrap.css" rel="stylesheet">
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body style="background-color: white">
<jsp:include page="/navbar"></jsp:include>
<div class="container">
    <c:choose>
        <c:when test="${not empty orders}">
            <c:forEach var="listValue" items="${orders}" varStatus="loop">
                <div style="background-color: white; padding: 5px">
                    <h3>${loop.index + 1}) ${listValue.name}</h3>
                    <p>${listValue.description}</p>
                    <p>${listValue.createDate}</p>
                    <p>Цена: ${listValue.count}</p>
                    <p>Теги: <c:forEach var="tag" items="${listValue.tags}">${tag.name} </c:forEach></p>
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