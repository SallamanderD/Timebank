<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty users}">
        <c:forEach var="listValue" items="${users}" varStatus="loop">
            <p>${loop.index + 1} ${listValue.username}</p>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Oops! Empty list.
    </c:otherwise>
</c:choose>
</body>
</html>