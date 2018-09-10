<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${account.firstName}</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        label {
            color: dimgray;
        }
    </style>
</head>
<body>
<div class="w3-cell-row" style="width: 860px">
    <div class="w3-container w3-cell" style="width: 190px">
        <br>
        <br>
        <br>
        <img src="/getImage" class="w3-round" style="width: 188px">
    </div>
    <div class="w3-container w3-cell" style="width: 670px">
        <br>
        <br>
        <div class="w3-container">
            <h3 class="w3-text-blue">
                <c:out value="${account.lastName}"/>
                <c:out value="${account.firstName}"/>
                <c:out value="${account.patronymicName}"/>
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
            <a href="/editProfile" class="w3-text-green">Редактировать профиль</a>
        </div>

    </div>
</div>
</body>
</html>
