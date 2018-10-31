<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Удаление профиля</title>
</head>
<body>
<div class="w3-container w3-content w3-display-middle">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <div class="w3-container w3-center">
                    <c:if test="${not empty sessionScope.userId}">
                    <p><label>Профиль удален</label>
                    <p>
                        </c:if>
                        <c:if test="${empty sessionScope.userId}">
                    <p><label>Ваш профиль удален</label>
                    <p>
                        </c:if>
                    <hr>
                    <a href="/login" class="w3-button w3-theme">Продолжить</a>
                    <p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>