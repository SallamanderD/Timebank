<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Выбор тегов</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/checkbox.css" rel="stylesheet">
    <script type="text/javascript">
        function check(checkbox){
            if(checkbox.checked == true){
                document.getElementById('tagString').value += document.getElementById(checkbox.id).value + ';';
            } else{
                var text = document.getElementById('tagString').value;
                document.getElementById('tagString').value = text.replace(checkbox.value + ';', '');
            }
        }
    </script>
</head>
<jsp:include page="/navbar"></jsp:include>
<body>
<div class="container" style="margin-top: 5%">
    <form action="/chooseTags" method="post">
        <div class="form-group">
            <input type="text" id="tagString" name="tagString" class="form-control" readonly>
            <c:choose>
                <c:when test="${not empty tags}">
                    <c:forEach var="listValue" items="${tags}" varStatus="loop">
                        <label for="checkbox${loop.index + 1}" class="btn btn-info">${listValue.name} <input onchange="check(checkbox${loop.index + 1})" type="checkbox" value="${listValue.name}" name="checkbox" id="checkbox${loop.index + 1}" class="badgebox"><span class="badge">&check;</span></label>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    Oops! Empty list.
                </c:otherwise>
            </c:choose>
            <div class="form-group input-group">
                <button type="submit" class="btn btn-success">Принять</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>