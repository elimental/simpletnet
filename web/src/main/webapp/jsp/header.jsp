<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="w3-top">
    <div class="w3-bar w3-blue">
        <a href="/login" class="w3-bar-item w3-button"><b>SIMPLENET</b></a>
        <a href="#" class="w3-bar-item w3-button"><b>Сообщения</b></a>
        <a href="/friends" class="w3-bar-item w3-button"><b>Друзья</b></a>
        <a href="/groups" class="w3-bar-item w3-button"><b>Группы</b></a>
        <a href="/logout" class="w3-bar-item  w3-button"><b>Выход
            <c:if test="${not empty sessionScope.userName}">
                (<c:out value="${sessionScope.userName}"/>)
            </c:if> </b></a>
        <form action="/search" method="get">
            <input class="w3-bar-item w3-right w3-border" type="text" name="search" placeholder="&#128269">
        </form>
    </div>
</div>
</body>
</html>
