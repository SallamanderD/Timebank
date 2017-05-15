<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Разместить заказ</title>
    <link href="${pageContext.request.contextPath}/css/checkbox.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/notification.css" rel="stylesheet">
    <script type="text/javascript">
        function check(checkbox) {
            if (checkbox.checked == true) {
                document.getElementById('tagString').value += document.getElementById(checkbox.id).value + ';';
            } else {
                var text = document.getElementById('tagString').value;
                document.getElementById('tagString').value = text.replace(checkbox.value + ';', '');
            }
        }
    </script>
</head>
<jsp:include page="/navbar"></jsp:include>
<body>
<div class="container" style="margin-top: 5%">
    <c:if test="${not empty errors}">
        <div class="notice notice-warning">
            <c:forEach items="${errors}" var="error">
                <p>${error}</p>
            </c:forEach>
        </div>
    </c:if>
    <form action="/placeorder" method="post">
        <div class="col-sm-12">
            <div class="form-group">
                <label for="name">Название:</label>
                <input type="text" value="${order.name}" name="name" class="form-control" id="name">
            </div>
            <div class="form-group">
                <label for="description">Описание:</label>
                <textarea class="form-control" value="${order.description}" name="description" rows="5" id="description"
                          style="max-width: 100%"></textarea>
            </div>
            <h3>Теги: </h3>
            <div class="form-group">
                <input type="text" id="tagString" name="tagString" class="form-control" readonly>
            </div>
            <c:choose>
                <c:when test="${not empty tags}">
                    <c:forEach var="listValue" items="${tags}" varStatus="loop">
                        <label for="checkbox${loop.index + 1}" class="btn btn-info">${listValue.name} <input
                                onchange="check(checkbox${loop.index + 1})" type="checkbox" value="${listValue.name}"
                                name="checkbox" id="checkbox${loop.index + 1}" class="badgebox"><span class="badge">&check;</span></label>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    Oops! Empty list.
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-sm-4">
            <div class="form-group">
                <label for="count">Цена:</label>
                <input type="number" value="0" name="count" class="form-control" id="count">
                <div class="radio">
                    <label><input type="radio" value="true" name="iot">Задание на дому?</label>
                </div>
                <button type="submit" class="btn btn-info">Разместить</button>
            </div>
        </div>
    </form>

</div>
</body>
</html>