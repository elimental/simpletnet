<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>SIMPLENET Вход</title>
</head>
<body>
<div class="w3-container w3-content w3-display-middle">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <div class="w3-container w3-center">
                    <label>Вход в сеть</label>
                </div>
                <hr>
                <form action="/loginCheck" method="post">
                    <label>E-mail адрес</label>
                    <input class="w3-input w3-border" type="email" name="email" required>
                    <label>Пароль</label>
                    <input class="w3-input w3-border" type="password" name="password" required>
                    <hr>
                    <div class="w3-container w3-center">
                        <button class="w3-button w3-theme" type="submit" style="width:80%"><i class="fa fa-sign-in"></i> Вход</button><br>
                        <input class="w3-check" type="checkbox" name="remember">
                        запомнить меня
                        <hr>
                        <a href="/registration" class="w3-text-green">регистрация</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
