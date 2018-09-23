<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Друзья</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <c:if test="${not empty friends}">
        <table class="w3-table-all w3-hoverable">
            <thead>
            <tr class="w3-light-blue">
                <th>Друзья</th>
            </tr>
            </thead>
            <c:forEach var="account" items="${friends}">
                <tr>
                    <td><a href="/userProfile?id=${account.id}">${account.firstName} ${account.lastName}</a>
                        <a href="/deleteFriend?id=${account.id}" class="w3-text-green">Удалить из друзей</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br>
    <c:if test="${not empty requestFromFriends}">
        <table class="w3-table-all w3-hoverable">
            <thead>
            <tr class="w3-light-blue">
                <th>Запросы от пользователей</th>
            </tr>
            </thead>
            <c:forEach var="account" items="${requestFromFriends}">
                <tr>
                    <td>${account.firstName} ${account.lastName}
                        <a href="/acceptFriend?id=${account.id}" class="w3-text-green">Принять</a>
                        <a href="/deleteFriend?id=${account.id}" class="w3-text-green">Отклонить</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br>
    <c:if test="${not empty requestedFriends}">
        <table class="w3-table-all w3-hoverable">
            <thead>
            <tr class="w3-light-blue">
                <th>Запросы к пользователям</th>
            </tr>
            </thead>
            <c:forEach var="account" items="${requestedFriends}">
                <tr>
                    <td>${account.firstName} ${account.lastName}
                        <a href="/deleteFriend?id=${account.id}" class="w3-text-green">Отменить запрос</a>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
