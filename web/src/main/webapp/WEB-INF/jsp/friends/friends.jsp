<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Друзья</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <c:if test="${empty friends && empty requestFromFriends && empty requestedFriends}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>У Вас пока нет друзей</label></p>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty requestFromFriends}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Заявки от пользователей</label></p>
                    <hr>
                    <c:forEach var="account" items="${requestFromFriends}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <img src="/getImage?type=account&id=${account.id}" alt="Avatar"
                                     class="w3-circle" style="height:55px;width:55px">
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <c:out value="${account.firstName}"></c:out>
                                    <c:out value="${account.lastName}"></c:out>
                                </h5>
                            </div>
                            <div class="w3-col m3">
                                <a href="/acceptFriendRequest?id=${account.id}" class="w3-button w3-theme"
                                   title="Добавить в друзья"><i
                                        class="fa fa-check"></i></a>
                                <a href="/deleteFriend?id=${account.id}" class="w3-button w3-theme"
                                   title="Отклонить заявку"><i
                                        class="fa fa-close"></i></a>
                            </div>
                        </div>
                        <br>
                    </c:forEach>
                </div>
            </div>
            <br>
        </c:if>

        <c:if test="${not empty requestedFriends}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Мои заявки</label></p>
                    <hr>
                    <c:forEach var="account" items="${requestedFriends}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <img src="/getImage?type=account&id=${account.id}" alt="Avatar"
                                     class="w3-circle" style="height:55px;width:55px">
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <c:out value="${account.firstName}"></c:out>
                                    <c:out value="${account.lastName}"></c:out>
                                </h5>
                            </div>
                            <div class="w3-col m3 w3-center">
                                <a href="/deleteFriend?id=${account.id}" class="w3-button w3-theme"
                                   title="Отменить заявку"><i
                                        class="fa fa-close"></i></a>
                            </div>
                        </div>
                        <br>
                    </c:forEach>
                </div>
            </div>
            <br>
        </c:if>

        <c:if test="${not empty friends}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Друзья</label></p>
                    <hr>
                    <c:forEach var="account" items="${friends}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <a href="/userProfile?id=${account.id}"><img
                                        src="/getImage?type=account&id=${account.id}"
                                        alt="Avatar" class="w3-circle"
                                        style="height:55px;width:55px"></a>
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <a href="/userProfile?id=${account.id}" style="text-decoration: none">
                                        <c:out value="${account.firstName}"></c:out>
                                        <c:out value="${account.lastName}"></c:out>
                                    </a>
                                </h5>
                            </div>
                            <div class="w3-col m3 w3-center">
                                <a href="/deleteFriend?id=${account.id}" class="w3-button w3-theme"
                                   title="Удалить из друзей"><i
                                        class="fa fa-close"></i></a>
                            </div>
                        </div>
                        <br>
                    </c:forEach>
                </div>
            </div>
            <br>
        </c:if>

    </div>
</div>
</body>
</html>
