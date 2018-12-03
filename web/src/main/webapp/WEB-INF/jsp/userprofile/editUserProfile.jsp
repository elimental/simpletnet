<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ include file="/WEB-INF/js/include_html.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Редактирование профиля</title>
    <meta charset="UTF-8">
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <form action="/checkEditUserProfile?${_csrf.parameterName}=${_csrf.token}" id="accountForm"
                      name="register" id="register"
                      enctype="multipart/form-data"
                      method="post">
                    <p>
                    <div class="w3-container w3-content" style="width: 190px">
                        <img src="/getImage?type=account&id=${account.id}" class="w3-circle"
                             style="height:180px;width:180px">
                    </div>
                    <br>
                    <label><b>Фото</b></label>
                    <input type="hidden" name="id" id="accountId" value="${account.id}">
                    <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img5mb"
                           onchange="uploadPhoto()">
                    <label><b>Имя</b></label>
                    <input class="w3-input w3-border" type="text" name="firstName" value="${account.firstName}"
                           required>
                    <label><b>Фамилия</b></label>
                    <input class="w3-input w3-border" type="text" name="lastName" value="${account.lastName}">
                    <label><b>Дата рождения</b></label>
                    <fmt:formatDate value="${account.birthDay}" pattern="dd.MM.yyyy" var="birthDay"></fmt:formatDate>
                    <input class="w3-input w3-border" id="datepicker" name="birthDay" value="${birthDay}">
                    <label><b>Skype</b></label>
                    <input class="w3-input w3-border" type="text" name="skype" value="${account.skype}">
                    <label><b>Icq</b></label>
                    <input class="w3-input w3-border" type="text" name="icq" value="${account.icq}">
                    <label><b>Дополнительная информация</b></label>
                    <textarea class="w3-input w3-border" style="resize: none" rows="5" cols="50"
                              name="additionalInfo">${account.additionalInfo}</textarea>
                    <label><b>Телефоны</b></label>
                    <div id="phones">
                        <c:set var="i" value="30"></c:set>
                        <c:forEach var="phone" items="${homePhones}">
                            <div class="w3-cell-row">
                                <div class="w3-cell">
                                    <input class="w3-input w3-border" id="addPhoneEditRule${i}"
                                           name="phones[${i}].number"
                                           value="${phone.number}">
                                </div>
                                <div class="w3-container w3-cell">
                                    <select class="w3-select w3-border" name="phones[${i}].type">
                                        <option value="HOME">Домашний</option>
                                        <option value="WORK">Рабочий</option>
                                    </select>

                                </div>
                                <div class="w3-cell">
                                    <button class="w3-button w3-theme" type="button" onclick="delPhoneLine(this)">-
                                    </button>
                                </div>
                            </div>
                            <c:set var="i" value="${i + 1}"></c:set>
                        </c:forEach>
                        <c:set var="i" value="60"></c:set>
                        <c:forEach var="phone" items="${workPhones}">
                            <div class="w3-cell-row">
                                <div class="w3-cell">
                                    <input class="w3-input w3-border" id="addPhoneEditRule${i}"
                                           name="phones[${i}].number"
                                           value="${phone.number}">
                                </div>
                                <div class="w3-container w3-cell">
                                    <select class="w3-select w3-border" name="phones[${i}].type">
                                        <option value="HOME">Домашний</option>
                                        <option selected value="WORK">Рабочий</option>
                                    </select>

                                </div>
                                <div class="w3-cell">
                                    <button class="w3-button w3-theme" type="button" onclick="delPhoneLine(this)">-
                                    </button>
                                </div>
                            </div>
                            <c:set var="i" value="${i + 1}"></c:set>
                        </c:forEach>
                    </div>
                    <p>
                        <button class="w3-button w3-theme" style="width: 50%" type="button" id="addPhoneBtn">Добавить
                            телефон
                        </button>
                    <p>
                        <button class="w3-button w3-theme" style="width: 50%" type="button" id="confirmEdit">
                            Сохранить
                        </button>
                </form>
            </div>
        </div>
        <br>
        <c:if test="${owner}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <label>Импорт/экспорт</label>
                    <form action="/importAccountXML" enctype="multipart/form-data" method="post">
                        <input class="w3-input w3-border" type="file" name="xmlFile" accept="application/xml">
                        <p>
                            <button class="w3-button w3-theme" type="submit" style="width: 50%">Импорт из XML</button>
                        <p>
                    </form>
                    <a href="/exportAccountXML?id=${account.id}" download class="w3-button w3-theme" style="width: 50%">
                        Экспорт в XML</a>
                    <p>
                </div>
            </div>
            <br>
        </c:if>
    </div>
</div>
</body>
</html>
