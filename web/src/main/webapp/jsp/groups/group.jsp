<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Группа</title>
</head>
<body>
<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">
        <!-- Left Column -->
        <div class="w3-col m3">
            <!-- Group profile -->
            <div class="w3-card w3-round w3-white">
                <div class="w3-container">
                    <h4 class="w3-center">
                        <c:out value="${group.name}"/>
                    </h4>
                    <p class="w3-center"><img src="/getImage?type=group&id=${group.id}" class="w3-circle"
                                              style="height:180px;width:180px" alt="Avatar"></p>
                    <p class="w3-center">
                        <a href="/groupMembers?groupId=${group.id}" class="w3-button w3-theme">Участники</a>
                        <c:if test="${not owner}">
                            <a title="Выйти из группы" href="/exitFromGroup?groupId=${group.id}"
                               class="w3-button w3-theme"><i class="fa fa-close"></i></a>
                        </c:if>
                    </p>
                    <hr>
                    <p class="w3-center"><label>Создана: </label><fmt:formatDate pattern="dd MMMM yyyy"
                                                                                 value="${group.createDate}"/>
                        г.</p>
                </div>
            </div>
            <br>

            <!-- Group description -->

            <c:if test="${not empty group.description}">
                <div class="w3-card w3-round w3-white">
                    <div class="w3-container">
                        <p class="w3-center"><label><b>Описание группы</b></label></p>
                        <hr>
                        <c:out value="${group.description}"/>
                        <p>
                    </div>
                </div>
                <br>
            </c:if>

            <!-- Moderator panel -->

            <c:if test="${showModeratorContent}">
                <div class="w3-card w3-round w3-white">
                    <div class="w3-container">
                        <p class="w3-center"><label>Панель модератора</label></p>
                        <hr>
                        <c:if test="${edit}">
                            <p class="w3-center">
                                <a href="/editGroup?id=${group.id}" class="w3-button w3-theme" style="width: 80%">Редактировать
                                    группу</a>
                            </p>
                        </c:if>
                        <c:if test="${delete}">
                            <p class="w3-center">
                                <a href="/confirmDelete?type=group&id=${group.id}" class="w3-button w3-theme"
                                   style="width: 80%">Удалить группу</a>
                            </p>
                        </c:if>
                        <c:if test="${showMakeRequestButton}">
                            <p class="w3-center">
                                <a href="/groupRequest?=${group.id}" class="w3-button w3-theme" style="width: 80%">Стать
                                    участником</a>
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
            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
                            <h6 class="w3-opacity">Сообщение для группы</h6>
                            <form action="/sendGroupMessage" name="group" method="get">
                                <input type="hidden" name="groupId" value="${group.id}">
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
            <!-- End Wall message form -->
            <!-- Wall messages -->
            <c:if test="${not empty groupMessages}">
                <c:forEach var="message" items="${groupMessages}">
                    <div class="w3-row-padding">
                        <div class="w3-col m12">
                            <div class="w3-card w3-round w3-white">
                                <div class="w3-container w3-padding"><br>
                                    <img src="/getImage?type=user&id=${message.author}" alt="Avatar"
                                         class="w3-left w3-circle w3-margin-right" style="height:55px;width:55px">
                                    <span class="w3-right w3-opacity"><fmt:formatDate pattern="dd.MM.yyyy hh.mm"
                                                                                      value="${message.createDate}"/></span>
                                    <c:import var="userName" url="/getUserName?id=${message.author}"></c:import>
                                    <h5>
                                        <c:out value="${userName}"/>
                                    </h5>
                                    <hr class="w3-clear">
                                    <p><c:out value="${message.text}"></c:out></p>
                                    <c:if test="${edit}">
                                        <a href="/deleteMessage?type=group&messageId=${message.id}&returnId=${group.id}"
                                           class="w3-button w3-theme-d1 w3-margin-bottom">Удалить</a>
                                    </c:if>
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
