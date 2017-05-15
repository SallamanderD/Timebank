<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Заказ</title>
    <script src="${pageContext.request.contextPath}/js/utility.js"></script>
    <script src="${pageContext.request.contextPath}/js/panel.js"></script>
    <link href="${pageContext.request.contextPath}/css/panel.css" rel="stylesheet"/>
    <script type="text/javascript">
        $(document).on('click', '.panel-heading span.clickable', function (e) {
            var $this = $(this);
            if (!$this.hasClass('panel-collapsed')) {
                $this.parents('.panel').find('.panel-body').slideUp();
                $this.addClass('panel-collapsed');
                $this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
            } else {
                $this.parents('.panel').find('.panel-body').slideDown();
                $this.removeClass('panel-collapsed');
                $this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
            }
        })
    </script>
</head>
<body style="background-color: white">
<jsp:include page="/navbar"></jsp:include>
<div class="container" style="margin-top: 5%">
    <div class="col-md-12">
        <h1>${order.name}</h1>
        <p>${order.description}</p>
        <div>
            <span class="badge"><span class="glyphicon glyphicon-calendar"
                                      aria-hidden="true"></span> Размещен ${order.createDate}</span>
            <div class="pull-right">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                <a href="#">${order.author.username}</a>
                <span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
                ${order.count}
                <span class="glyphicon glyphicon-tags" aria-hidden="true"></span>
                Теги:
                <c:forEach var="tag" items="${order.tags}"><span
                        class="label label-success">${tag.name}</span></c:forEach>
            </div>
        </div>
        <c:if test="${type == true && order.isCompleted == false}">
            <div class="pull-right"><a onclick="post('/unassign', {orderId : ${order.id}})">Отписаться?</a></div>
        </c:if>
        <c:if test="${empty type and not empty sessionScope.get('userId') && order.isCompleted == false}">
            <div class="pull-right"><a onclick="post('/assign', {orderId : ${order.id}})">Подписаться?</a></div>
        </c:if>
        <hr>
        <c:if test="${order.author.id == sessionScope.get('userId')}">
            <h3>Управление заказом:</h3>
            <div class="form-group">
                <c:if test="${order.executer != null}">
                    <p>Исполнитель заказа - ${order.executer.username}, <a>Отписать заказ от этого пользователя?</a></p>
                </c:if>
                <c:if test="${order.executer == null}">
                    <label for="sel1">Выберите пользователя для выполнения:</label>
                    <select class="form-control" id="sel1" name="userId">
                        <c:forEach items="${order.possibleExecuters}" var="u">
                            <option value="${u.id}">${u.username}</option>
                        </c:forEach>
                    </select>
                    <div class="form-group">
                        <button class="btn btn-success" type="button"
                                onclick="post('/appoint', {orderId : ${order.id}, userId : document.getElementById('sel1').value})">
                            Назначить
                        </button>
                    </div>
                </c:if>

            </div>
        </c:if>
        <h2>Комментарии: </h2>
        <jsp:include page="/orderCommentaries/${order.id}"></jsp:include>
    </div>
</div>
</body>
</html>