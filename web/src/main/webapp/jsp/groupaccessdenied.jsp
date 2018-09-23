<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Доступ запрещен</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<script src="/js/script.js"></script>
<body>
<div class="w3-display-container w3-display-middle">
    <h2 class="w3-text-blue">Просмотр данной группы для Вас закрыт</h2><br>
    <a href="/groupRequest?=${id}" class="w3-button w3-blue">Стать участником</a>
    <br>
    <br>
    <button class="w3-btn w3-blue" onclick="goBack()">Назад</button>
</div>
</body>
</html>