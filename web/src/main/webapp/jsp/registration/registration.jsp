<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <script src="/js/script.js"></script>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <form action="/regCheck" name="register" enctype="multipart/form-data"
                      onsubmit="return validatePassword()"
                      method="post">
                    <label><b>E-mail адрес</b></label>
                    <input class="w3-input w3-border" type="email" name="email" required>
                    <label><b>Пароль</b></label>
                    <input class="w3-input w3-border" type="password" name="password">
                    <label><b>Подтверждение пароля</b></label>
                    <input class="w3-input w3-border" type="password" name="rpassword">
                    <label><b>Имя</b></label>
                    <input class="w3-input w3-border" type="text" name="first_name" required>
                    <label><b>Фамилия</b></label>
                    <input class="w3-input w3-border" type="text" name="last_name">
                    <label><b>Отчество</b></label>
                    <input class="w3-input w3-border" type="text" name="patronymic_name">
                    <label><b>Дата рождения</b></label>
                    <input class="w3-input w3-border" type="date" name="birthday">
                    <label><b>Skype</b></label>
                    <input class="w3-input w3-border" type="text" name="skype">
                    <label><b>Icq</b></label>
                    <input class="w3-input w3-border" type="text" name="icq">
                    <label><b>Дополнительная информация</b></label>
                    <textarea class="w3-input w3-border" rows="5" cols="50" name="addinfo"></textarea>
                    <label><b>Фотография профиля</b></label>
                    <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img"
                           onchange="uploadPhoto()">
                    <label><b>Телефоны</b></label>
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
                                <button class="w3-button w3-theme" type="button" onclick="deletePhoneLine(this)">-</button>
                            </div>
                        </div>
                        <button class="w3-button w3-theme" type="button" onclick="addPhoneLine(this)">Добавить телефон
                        </button>
                    </div>
                    <br>
                    <button class="w3-button w3-theme" type="submit">Регистрация</button>
                    <br>
                </form>
                <br>
            </div>
        </div>
    </div>
</div>
<!-- End Page Container -->
</body>
</html>
