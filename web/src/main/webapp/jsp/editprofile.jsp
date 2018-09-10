<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>${account.firstName}</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<script src="/js/script.js"></script>
<script src="/js/edit.js"></script>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <form action="/editProfileCheck" name="register" enctype="multipart/form-data" method="post">
        <label class="w3-text-blue"><b>Фото</b></label>
        <div style="width: 190px">
            <img src="/getImage" class="w3-round" style="width: 188px">
        </div>
        <br>
        <input class="w3-input w3-border" type="file" name="photo" accept="image/jpeg" id="photo"
               onchange="uploadPhoto()">
        <label class="w3-text-blue"><b>Имя</b></label>
        <input class="w3-input w3-border" type="text" name="first_name" value=${account.firstName} required>
        <label class="w3-text-blue"><b>Фамилия</b></label>
        <input class="w3-input w3-border" type="text" name="last_name" value=${account.lastName}>
        <label class="w3-text-blue"><b>Отчество</b></label>
        <input class="w3-input w3-border" type="text" name="patronymic_name" value=${account.patronymicName}>
        <label class="w3-text-blue"><b>Дата рождения</b></label>
        <input class="w3-input w3-border" type="date" name="birthday" value=${account.birthDay}>
        <label class="w3-text-blue"><b>Skype</b></label>
        <input class="w3-input w3-border" type="text" name="skype" value=${account.skype}>
        <label class="w3-text-blue"><b>Icq</b></label>
        <input class="w3-input w3-border" type="text" name="icq" value=${account.icq}>
        <label class="w3-text-blue"><b>Дополнительная информация</b></label>
        <textarea class="w3-input w3-border" rows="5" cols="50" name="addinfo">${account.additionalInfo}</textarea>
        <label class="w3-text-blue"><b>Телефоны</b></label>
        <div id="phones">
            <c:forEach var="phone" items="${homePhones}">
                <div class="w3-cell-row">
                    <div class="w3-cell">
                        <input class="w3-input w3-border" type="tel" name="phone" pattern="[0-9]+"
                               value=${phone.number}>
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
            </c:forEach>
            <c:forEach var="phone" items="${workPhones}">
                <div class="w3-cell-row">
                    <div class="w3-cell">
                        <input class="w3-input w3-border" type="tel" name="phone" pattern="[0-9]+"
                               value=${phone.number}>
                    </div>
                    <div class="w3-container w3-cell">
                        <select class="w3-select w3-border" name="phone_type">
                            <option value="home">Домашний</option>
                            <option selected value="work">Рабочий</option>
                        </select>

                    </div>
                    <div class="w3-cell">
                        <button class="w3-btn w3-blue" type="button" onclick="deletePhoneLine(this)">-</button>
                    </div>
                </div>
            </c:forEach>
            <button class="w3-btn w3-blue" type="button" onclick="addPhoneLine(this)">Добавить телефон</button>
        </div>
        <br>
        <button class="w3-btn w3-blue" type="submit">Сохранить</button>
        <br>
    </form>
</div>
</body>
</html>
