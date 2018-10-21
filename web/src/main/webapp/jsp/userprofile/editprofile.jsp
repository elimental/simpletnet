<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Редактирование профиля</title>
</head>
<script src="/js/script.js"></script>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <form action="/editProfileCheck" name="register" id="register" enctype="multipart/form-data"
                      method="post">
                    <p>
                    <div class="w3-container w3-content" style="width: 190px">
                        <img src="/getImage?type=user&id=${account.id}" class="w3-circle" style="height:180px;width:180px">
                    </div>
                    <br>
                    <label><b>Фото</b></label>
                    <input type="hidden" name="id" value="${account.id}">
                    <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img"
                           onchange="uploadPhoto()">
                    <label><b>Имя</b></label>
                    <input class="w3-input w3-border" type="text" name="first_name" value="${account.firstName}"
                           required>
                    <label><b>Фамилия</b></label>
                    <input class="w3-input w3-border" type="text" name="last_name" value="${account.lastName}">
                    <label><b>Отчество</b></label>
                    <input class="w3-input w3-border" type="text" name="patronymic_name"
                           value="${account.patronymicName}">
                    <label><b>Дата рождения</b></label>
                    <input class="w3-input w3-border" type="date" name="birthday" value="${account.birthDay}">
                    <label><b>Skype</b></label>
                    <input class="w3-input w3-border" type="text" name="skype" value="${account.skype}">
                    <label><b>Icq</b></label>
                    <input class="w3-input w3-border" type="text" name="icq" value="${account.icq}">
                    <label><b>Дополнительная информация</b></label>
                    <textarea class="w3-input w3-border" rows="5" cols="50"
                              name="addinfo">${account.additionalInfo}</textarea>
                    <label><b>Телефоны</b></label>
                    <div id="phones">
                        <c:forEach var="phone" items="${homePhones}">
                            <div class="w3-cell-row">
                                <div class="w3-cell">
                                    <input class="w3-input w3-border" type="tel" name="phone" pattern="[0-9]+"
                                           value="${phone.number}">
                                </div>
                                <div class="w3-container w3-cell">
                                    <select class="w3-select w3-border" name="phone_type">
                                        <option value="home">Домашний</option>
                                        <option value="work">Рабочий</option>
                                    </select>

                                </div>
                                <div class="w3-cell">
                                    <button class="w3-button w3-theme" type="button" onclick="deletePhoneLine(this)">-
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                        <c:forEach var="phone" items="${workPhones}">
                            <div class="w3-cell-row">
                                <div class="w3-cell">
                                    <input class="w3-input w3-border" type="tel" name="phone" pattern="[0-9]+"
                                           value="${phone.number}">
                                </div>
                                <div class="w3-container w3-cell">
                                    <select class="w3-select w3-border" name="phone_type">
                                        <option value="home">Домашний</option>
                                        <option selected value="work">Рабочий</option>
                                    </select>

                                </div>
                                <div class="w3-cell">
                                    <button class="w3-button w3-theme" type="button" onclick="deletePhoneLine(this)">-
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                        <button class="w3-button w3-theme" type="button" onclick="addPhoneLine(this)">Добавить телефон
                        </button>
                    </div>
                    <br>
                    <button class="w3-button w3-theme" type="button" onclick="confirmEdit(this)">Сохранить</button>
                    <br>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
