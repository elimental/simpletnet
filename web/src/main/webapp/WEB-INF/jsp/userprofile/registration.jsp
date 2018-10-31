<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ include file="/WEB-INF/js/include_html.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <form action="/checkRegistration" id="accountForm" enctype="multipart/form-data" method="post">
                    <label><b>E-mail адрес</b></label>
                    <input class="w3-input w3-border" type="email" name="email" required>
                    <label><b>Пароль</b></label>
                    <input class="w3-input w3-border" id="password" type="password" name="password">
                    <label><b>Подтверждение пароля</b></label>
                    <input class="w3-input w3-border" type="password" name="rpassword">
                    <label><b>Имя</b></label>
                    <input class="w3-input w3-border" type="text" name="firstName">
                    <label><b>Фамилия</b></label>
                    <input class="w3-input w3-border" type="text" name="lastName">
                    <label><b>Отчество</b></label>
                    <input class="w3-input w3-border" type="text" name="patronymicName">
                    <label><b>Дата рождения</b></label>
                    <input class="w3-input w3-border" id="datepicker" name="birthDay">
                    <label><b>Skype</b></label>
                    <input class="w3-input w3-border" type="text" name="skype">
                    <label><b>Icq</b></label>
                    <input class="w3-input w3-border" type="text" name="icq">
                    <label><b>Дополнительная информация</b></label>
                    <textarea class="w3-input w3-border" style="resize: none" rows="5" cols="50"
                              name="additionalInfo"></textarea>
                    <label><b>Фотография профиля</b></label>
                    <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img5mb">
                    <label><b>Телефоны</b></label>
                    <div id="phones">
                        <div class="w3-cell-row">
                            <div class="w3-cell">
                                <input class="phone w3-input w3-border" id="addPhoneEditRule" name="phones[0].number">
                            </div>
                            <div class="w3-container w3-cell">
                                <select class="w3-select w3-border" name="phones[0].type">
                                    <option value="1">Домашний</option>
                                    <option value="2">Рабочий</option>
                                </select>
                            </div>
                            <div class="w3-cell">
                                <button class="w3-button w3-theme" type="button" onclick="delPhoneLine(this)">-</button>
                            </div>
                        </div>
                    </div>
                    <p>
                        <button class="w3-button w3-theme" style="width: 50%" type="button" id="addPhoneBtn">Добавить
                            телефон
                        </button>
                    <p>
                        <button class="w3-button w3-theme" style="width: 50%" type="submit">Регистрация</button>
                </form>
                <br>
            </div>
        </div>
    </div>
</div>
<!-- End Page Container -->
</body>
</html>
