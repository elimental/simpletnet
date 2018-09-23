<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>SIMPLENET Вход</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="w3-display-container w3-display-middle">
    <label class="w3-text-red">Неверное имя пользователя или пароль</label><br>
    <form action="/loginCheck" method="post">

        <label class="w3-text-blue"><b>E-mail адрес</b></label>
        <input class="w3-input w3-border" type="email" name="email" required>

        <label class="w3-text-blue"><b>Пароль</b></label>
        <input class="w3-input w3-border" type="password" name="password" required><br>

        <button class="w3-btn w3-blue" type="submit">Вход</button>
        <br>

        <input class="w3-check" type="checkbox">
        <label>Запомнить меня</label>

    </form>
    <a href="/registration" class="w3-text-green">Регистрация</a>
</div>
</body>
</html>