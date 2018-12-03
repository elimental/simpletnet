<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Профиль</title>
    <meta charset="UTF-8">
</head>
<body>
<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">
        <!-- Left Column -->
        <div class="w3-col m3">
            <!-- Profile -->
            <div class="w3-card w3-round w3-white">
                <div class="w3-container">
                    <h4 class="w3-center">
                        <c:out value="${account.firstName}"/>
                        <c:out value="${account.lastName}"/>
                        <c:if test="${admin}"> (Администратор)</c:if>
                    </h4>
                    <p class="w3-center"><img src="/getImage?type=account&id=${account.id}" class="w3-circle"
                                              style="height:180px;width:180px" alt="Avatar"></p>
                    <c:if test="${not owner}">
                        <p class="w3-center">
                            <a href="/chat?id=${account.id}" class="w3-button w3-theme">Написать сообщение</a>
                        </p>
                    </c:if>
                    <hr>
                    <p class="w3-center"><label>В сети с: </label><fmt:formatDate pattern="dd MMMM yyyy"
                                                                                  value="${account.regDate}"/></p>
                </div>
            </div>
            <br>

            <!-- Contacts -->

            <div class="w3-card w3-round w3-white">
                <div class="w3-container">
                    <p class="w3-center"><label>Мои контакты</label></p>
                    <hr>
                    <c:if test="${not empty account.skype}">
                        <p><img src="/images/skype_icon.jpeg" style="height: 19px;width: 19px">&#160&#160&#160&#160
                            <c:out value="${account.skype}"/></p>
                    </c:if>

                    <c:if test="${not empty account.icq}">
                        <p><img src="/images/icq_icon.png" style="height: 19px;width: 19px">&#160&#160&#160&#160
                            <c:out value="${account.icq}"/></p>
                    </c:if>

                    <c:forEach var="phone" items="${homePhones}">
                        <p><img src="/images/phone_icon.png" style="height: 16px;width: 16px">&#160&#160&#160&#160
                            <c:out value="${phone.number}"/> дом.</p>
                    </c:forEach>

                    <c:forEach var="phone" items="${workPhones}">
                        <p><img src="/images/phone_icon.png" style="height: 16px;width: 16px">&#160&#160&#160&#160
                            <c:out value="${phone.number}"/> раб.</p>
                    </c:forEach>
                </div>
            </div>
            <br>
            <!-- Additional information -->
            <div class="w3-card w3-round w3-white">
                <div class="w3-container">
                    <c:if test="${not empty account.additionalInfo or not empty account.birthDay}">
                    <p class="w3-center"><label><b>Обо мне</b></label></p>
                    <hr>
                    <c:if test="${not empty account.birthDay}">
                        <p><i class="fa fa-birthday-cake w3-margin-right w3-text-theme"></i>
                            <fmt:formatDate pattern="dd MMM yyyy" value="${account.birthDay}"/></p>
                        <c:if test="${not empty account.additionalInfo}">
                            <hr>
                        </c:if>
                    </c:if>

                    <c:out value="${account.additionalInfo}"/>
                    <p>
                        </c:if>
                </div>
            </div>
            <br>
            <!-- Admin panel -->
            <c:if test="${showAdminContent}">
                <div class="w3-card w3-round w3-white">
                    <div class="w3-container">
                        <p class="w3-center"><label>Панель администратора</label></p>
                        <hr>
                        <p class="w3-center">
                            <a href="/editUserProfile?id=${account.id}" class="w3-button w3-theme" style="width: 80%">Редактировать
                                профиль</a>
                        </p>
                        <p class="w3-center">
                            <a href="/confirmDeleteUserProfile?id=${account.id}" class="w3-button w3-theme"
                               style="width: 80%">Удалить
                                профиль</a>
                        </p>
                        <c:if test="${showMakeAdminButton}">
                            <p class="w3-center">
                                <a href="/makeAdmin?id=${account.id}" class="w3-button w3-theme" style="width: 80%">Сделать
                                    администратором</a>
                            </p>
                        </c:if>
                        <c:if test="${showAddFriendButton}">
                            <p class="w3-center">
                                <a href="/friendRequest?id=${account.id}" class="w3-button w3-theme" style="width: 80%">Добавить
                                    в друзья</a>
                            </p>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>
        <!-- End Left Column -->
        <!-- Right Column -->
        <!-- Wall message form -->
        <div class="w3-col m7">
            <c:if test="${owner}">
                <div class="w3-row-padding">
                    <div class="w3-col m12">
                        <div class="w3-card w3-round w3-white">
                            <div class="w3-container w3-padding">
                                <h6 class="w3-opacity">Чё ваще происходит?</h6>
                                <form action="/sendWallMessage" name="wall" method="get">
                                    <p contenteditable="true" class="w3-border w3-padding" id="message"></p>
                                    <button type="button" class="w3-button w3-theme" onclick="submitMessageForm(this)">
                                        <i class="fa fa-pencil"></i>
                                         Отправить
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
            </c:if>
            <!-- End Wall message form -->
            <!-- Wall messages -->
            <c:if test="${not empty wallMessages}">
                <c:forEach var="message" items="${wallMessages}">
                    <div class="w3-row-padding">
                        <div class="w3-col m12">
                            <div class="w3-card w3-round w3-white">
                                <div class="w3-container w3-padding"><br>
                                    <img src="/getImage?type=account&id=${account.id}" alt="Avatar"
                                         class="w3-left w3-circle w3-margin-right" style="height:55px;width:55px">
                                    <span class="w3-right w3-opacity"><fmt:formatDate pattern="dd MMM yyyy"
                                                                                      value="${message.createDate}"/></span>
                                    <h5>
                                        <c:out value="${account.firstName}"/>
                                        <c:out value="${account.lastName}"/>
                                    </h5>
                                    <hr class="w3-clear">
                                    <p><c:out value="${message.text}"></c:out></p>
                                    <a href="/deleteWallMessage?messageId=${message.id}&returnId=${account.id}"
                                       class="w3-button w3-theme-d1 w3-margin-bottom">Удалить</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                </c:forEach>
            </c:if>
        </div>
        <!-- End right column -->
    </div>
</div>
<!-- End Page Container -->
</body>
</html>
