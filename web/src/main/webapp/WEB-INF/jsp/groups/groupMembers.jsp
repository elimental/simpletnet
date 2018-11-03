<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Участники группы</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <c:if test="${showCandidates && not empty candidates}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Заявки от пользователей</label></p>
                    <hr>
                    <c:forEach var="candidat" items="${candidates}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <a href="/userProfile?id=${candidat.id}"><img
                                        src="/getImage?type=account&id=${candidat.id}" alt="Avatar"
                                        class="w3-circle" style="height:55px;width:55px"></a>
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <a href="/userProfile?id=${candidat.id}" style="text-decoration: none">
                                        <c:out value="${candidat.firstName}"></c:out>
                                        <c:out value="${candidat.lastName}"></c:out>
                                    </a>
                                </h5>
                            </div>
                            <div class="w3-col m3">
                                <a href="/acceptGroupRequest?userId=${candidat.id}&groupId=${groupId}"
                                   class="w3-button w3-theme"><i
                                        class="fa fa-check"></i></a>
                                <a href="/rejectGroupRequest?userId=${candidat.id}&groupId=${groupId}"
                                   class="w3-button w3-theme"><i
                                        class="fa fa-close"></i></a>
                            </div>
                        </div>
                        <br>
                    </c:forEach>
                </div>
            </div>
            <br>
        </c:if>
        <c:if test="${not empty moderators}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Модераторы</label></p>
                    <hr>
                    <c:forEach var="moderator" items="${moderators}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <a href="/userProfile?id=${moderator.id}"><img
                                        src="/getImage?type=account&id=${moderator.id}" alt="Avatar"
                                        class="w3-circle" style="height:55px;width:55px"></a>
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <a href="/userProfile?id=${moderator.id}" style="text-decoration: none">
                                        <c:out value="${moderator.firstName}"></c:out>
                                        <c:out value="${moderator.lastName}"></c:out>
                                    </a>
                                </h5>
                            </div>
                            <div class="w3-col m3">
                            </div>
                        </div>
                        <br>
                    </c:forEach>
                </div>
            </div>
            <br>
        </c:if>
        <c:if test="${not empty simpleMembers}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Участники</label></p>
                    <hr>
                    <c:forEach var="member" items="${simpleMembers}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <a href="/userProfile?id=${member.id}">
                                    <img src="/getImage?type=account&id=${member.id}" alt="Avatar"
                                         class="w3-circle" style="height:55px;width:55px"></a>
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <a href="/userProfile?id=${member.id}" style="text-decoration: none">
                                        <c:out value="${member.firstName}"></c:out>
                                        <c:out value="${member.lastName}"></c:out>
                                    </a>
                                </h5>
                            </div>
                            <div class="w3-col m3">
                                <c:if test="${delete}">
                                    <a title="Сделать модератором"
                                       href="/makeModerator?userId=${member.id}&groupId=${groupId}"
                                       class="w3-button w3-theme"><i class="fa fa-eye"></i></a>
                                    <a title="Удалить пользователя"
                                       href="/deleteFromGroup?userId=${member.id}&groupId=${groupId}"
                                       class="w3-button w3-theme"><i class="fa fa-close"></i></a>
                                </c:if>
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
