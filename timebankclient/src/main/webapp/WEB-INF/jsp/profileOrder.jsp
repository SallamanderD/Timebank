<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/js/utility.js"></script>
</head>
<body>
<h2>Заказы, которые разместили вы: </h2>
<c:choose>
    <c:when test="${not empty authorOrders}">
        <c:forEach var="order" items="${authorOrders}" varStatus="loop">
            <h4><a href="/order/${order.id}">${order.name}</a></h4>
            <c:if test="${order.isCompletedByExecuter && order.isCompletedByAuthor == false && order.executer != null}">
                <p>Заказ подтвержден исполнителем как выполненый. Вы согласны с этим? <a
                        onclick="post('/markOrderCompletedByAuthor', {orderId : ${order.id}})">Да!</a> <a>Нет, сделать
                    заказ свободным.</a></p>
            </c:if>
            <c:if test="${order.isCompleted}">
                <p>Заказ выполнен, поздравляем! <a>Скрыть заказ?</a></p>
            </c:if>
            <c:if test="${order.isCompletedByExecuter == false && order.isCompletedByAuthor == false && order.executer != null}">
                <p><a onclick="post('/markOrderCompletedByAuthor', {orderId : ${order.id}})">Подтвердить выполнение заказа?</a></p>
            </c:if>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Вы еще не разместили ни одного заказа. <a href="/placeorder">Хотите сделать это сейчас?</a>
    </c:otherwise>
</c:choose>
<h2>Заказы, на которые вы подписались: </h2>
<c:choose>
    <c:when test="${not empty assignOrders}">
        <c:forEach var="order" items="${assignOrders}" varStatus="loop">
            <h4><a href="/order/${order.id}">${order.name}</a></h4>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Вы еще не подписались ни на один заказ.
    </c:otherwise>
</c:choose>
<h2>Заказы, на которые вы утверждены как выполнитель: </h2>
<c:choose>
    <c:when test="${not empty executingOrders}">
        <c:forEach var="order" items="${executingOrders}" varStatus="loop">
            <h4><a href="/order/${order.id}">${order.name}</a></h4>
            <c:if test="${order.isCompleted}">
                <p>Заказ выполнен, поздравляем с выполнением. <a>Скрыть заказ?</a></p>
            </c:if>
            <c:if test="${order.isCompletedByExecuter != true}">
                <p>Вы выполнили заказ? <a onclick="post('/markOrderCompletedByExecuter', {orderId : ${order.id}})">Да!</a></p>
            </c:if>
            <c:if test="${order.isCompletedByExecuter == true && order.isCompletedByAuthor == false}">
                <p>Автор еще не подтвердил выполнение заказа, ожидайте.</p>
            </c:if>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Вы еще не были утверждены ни на один заказ.
    </c:otherwise>
</c:choose>
</body>
</html>