<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Main Page</title>
    <link href="webjars/bootstrap/3.2.0/css/bootstrap.css" rel="stylesheet">
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>
<c:choose>
    <c:when test="${not empty users}">
        <c:forEach var="listValue" items="${users}" varStatus="loop">
            <p>${loop.index + 1}) ${listValue.username}</p>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Oops! Empty list.
    </c:otherwise>
</c:choose>
<button type="button" class="btn btn-default">asdasdasdc</button>
</body>
</html>