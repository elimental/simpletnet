<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Результаты поиска</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <table class="w3-table-all w3-hoverable">
        <thead>
        <tr class="w3-light-blue">
            <th>Пользователи</th>
        </tr>
        </thead>
        <c:if test="${empty accounts}">
            <tr>
                <td>Нет результатов</td>
            </tr>
        </c:if>
        <c:if test="${not empty accounts}">
            <c:forEach var="account" items="${accounts}">
                <tr>
                    <td><a href="/userProfile?id=${account.id}">${account.firstName} ${account.lastName}</a></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <br>
    <table class="w3-table-all w3-hoverable">
        <thead>
        <tr class="w3-light-blue">
            <th>Группы</th>
        </tr>
        </thead>
        <c:if test="${empty groups}">
            <tr>
                <td>Нет результатов</td>
            </tr>
        </c:if>
        <c:if test="${not empty groups}">
            <c:forEach var="group" items="${groups}">
                <tr>
                    <td><a href="/group?id=${group.id}">${group.name}</a></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
</body>
</html>
