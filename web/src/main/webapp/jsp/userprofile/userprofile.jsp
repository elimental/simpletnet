<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Профиль</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        label {
            color: dimgray;
        }

        .dot {
            height: 10px;
            width: 10px;
            background-color: #66a3ff;
            border-radius: 50%;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="w3-cell-row" style="width: 1160px">
    <div class="w3-container w3-cell" style="width: 190px">
        <br>
        <br>
        <br>
        <img src="/getImage?type=user&id=${account.id}" class="w3-round" style="width: 188px">
    </div>
    <div class="w3-container w3-cell" style="width: 470px">
        <br>
        <br>
        <div class="w3-container">
            <h3 class="w3-text-blue">
                <c:out value="${account.lastName}"/>
                <c:out value="${account.firstName}"/>
                <c:out value="${account.patronymicName}"/>
                <c:if test="${admin}"> (Администратор)</c:if>
            </h3>
            <c:if test="${not empty account.birthDay}">
                <label>Дата рождения: </label><fmt:formatDate pattern="dd.MM.yyyy" value="${account.birthDay}"/><br>
            </c:if>
            <c:if test="${not empty account.icq}">
                <label>Icq: </label><c:out value="${account.icq}"/> <br>
            </c:if>
            <c:if test="${not empty account.skype}">
                <label>Skype: </label><c:out value="${account.skype}"/> <br>
            </c:if>
            <c:if test="${not empty account.additionalInfo}">
                <label>Дополнительная информация: </label><c:out value="${account.additionalInfo}"/> <br>
            </c:if>
            <c:forEach var="phone" items="${homePhones}">
                <label>Домашний телефон: </label><c:out value="${phone.number}"/><br>
            </c:forEach>
            <c:forEach var="phone" items="${workPhones}">
                <label>Рабочий телефон: </label><c:out value="${phone.number}"/><br>
            </c:forEach>
            <label>Дата регистрации: </label><fmt:formatDate pattern="dd.MM.yyyy" value="${account.regDate}"/>
            <br>
            <br>
            <c:if test="${editAndDelete}">
                <a href="/editProfile?id=${account.id}" class="w3-button w3-blue">Редактировать профиль</a>
                <br>
                <br>
                <a href="/confirmDelete?type=user&id=${account.id}" class="w3-button w3-blue">Удалить профиль</a>
                <br>
                <br>
            </c:if>

            <c:if test="${allowMakeAdmin}">
                <a href="/makeAdmin?id=${account.id}" class="w3-button w3-blue">Сделать администратором</a>
                <br>
                <br>
            </c:if>
            <c:if test="${showAddFriendButton}">
                <a href="/friendRequest?id=${account.id}" class="w3-button w3-blue">Добавить в друзья</a>
            </c:if>
        </div>
    </div>
    <div class="w3-container w3-cell" style="width: 500px">
        <br>
        <br>
        <br>
        <c:if test="${owner}">
        <form action="/sendWallMessage" name="wall" method="get">
            <textarea class="w3-input w3-border" placeholder="Что нового?" rows="2" cols="50"
                      name="wallmessage"></textarea>
            <button class="w3-btn w3-blue" type="submit">Отправить</button>
        </form>
        <br>
        </c:if>
        <c:if test="${not empty wallMessages}">
            <c:forEach var="message" items="${wallMessages}">
                <p><span class="dot"></span>&#160&#160&#160${message.text}</p>
                <br>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
</html>
