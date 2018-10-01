<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<script src="/js/script.js"></script>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <form action="/regCheck" name="register" enctype="multipart/form-data" onsubmit="return validatePassword()"
          method="post">

        <label class="w3-text-blue"><b>E-mail адрес</b></label>
        <input class="w3-input w3-border" type="email" name="email" required>

        <label class="w3-text-blue"><b>Пароль</b></label>
        <input class="w3-input w3-border" type="password" name="password">

        <label class="w3-text-blue"><b>Подтверждение пароля</b></label>
        <input class="w3-input w3-border" type="password" name="rpassword">

        <label class="w3-text-blue"><b>Имя</b></label>
        <input class="w3-input w3-border" type="text" name="first_name" required>

        <label class="w3-text-blue"><b>Фамилия</b></label>
        <input class="w3-input w3-border" type="text" name="last_name">

        <label class="w3-text-blue"><b>Отчество</b></label>
        <input class="w3-input w3-border" type="text" name="patronymic_name">

        <label class="w3-text-blue"><b>Дата рождения</b></label>
        <input class="w3-input w3-border" type="date" name="birthday">

        <label class="w3-text-blue"><b>Skype</b></label>
        <input class="w3-input w3-border" type="text" name="skype">

        <label class="w3-text-blue"><b>Icq</b></label>
        <input class="w3-input w3-border" type="text" name="icq">

        <label class="w3-text-blue"><b>Дополнительная информация</b></label>
        <textarea class="w3-input w3-border" rows="5" cols="50" name="addinfo"></textarea>

        <label class="w3-text-blue"><b>Фотография профиля</b></label>
        <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img"
               onchange="uploadPhoto()">

        <label class="w3-text-blue"><b>Телефоны</b></label>
        <div id="phones">
            <div class="w3-cell-row">
                <div class="w3-cell">
                    <input class="w3-input w3-border" type="tel" name="phone" pattern="[0-9]+">
                </div>
                <div class="w3-container w3-cell">
                    <select class="w3-select w3-border" name="phone_type">
                        <option value="home">Домашний</option>
                        <option value="work">Рабочий</option>
                    </select>
                </div>
                <div class="w3-cell">
                    <button class="w3-btn w3-blue" type="button" onclick="deletePhoneLine(this)">-</button>
                </div>
            </div>
            <button class="w3-btn w3-blue" type="button" onclick="addPhoneLine(this)">Добавить телефон</button>
        </div>
        <br>
        <button class="w3-btn w3-blue" type="submit">Регистрация</button>
        <br>
    </form>
</div>
</body>
</html>
