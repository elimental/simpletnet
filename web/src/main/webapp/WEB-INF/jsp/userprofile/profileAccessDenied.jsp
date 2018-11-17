<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Доступ запрещен</title>
</head>
<body>
<div class="w3-container w3-content w3-display-middle">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <div class="w3-container w3-center">
                    <p><label>Нет доступа</label>
                    <p>
                    <hr>
                    <p>Просмотр данного профиля для Вас закрыт</p>
                    <c:if test="${alreadyRequested}">
                        <p>Кто то из вас уже подал запрос на добавление в друзья</p>
                    </c:if>
                    <c:if test="${not alreadyRequested}">
                        <a href="/friendRequest?id=${requestedUserId}" class="w3-button w3-theme">Добавить в друзья</a>
                    </c:if>
                    <button class="w3-btn w3-theme" onclick="goBack()">Назад</button>
                    <p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
