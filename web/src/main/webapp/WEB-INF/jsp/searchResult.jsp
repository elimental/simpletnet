<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Результат поиска</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:600px;margin-top:80px">
    <div class="w3-row">
        <div class="w3-col s6">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container">
                    <p class="w3-center"><label>Пользователи</label></p>
                    <hr>
                    <c:if test="${empty accounts}">
                        <p class="w3-center">Не найдено</p>
                    </c:if>
                    <c:if test="${not empty accounts}">
                        <c:forEach var="account" items="${accounts}">
                            <div class="w3-row">
                                <div class="w3-col m3">
                                    <a href="/userProfile?id=${account.id}"><img
                                            src="/getImage?type=user&id=${account.id}"
                                            alt="Avatar" class="w3-circle"
                                            style="height:30px;width:30px"></a>
                                </div>
                                <div class="w3-col m6">
                                    <a href="/userProfile?id=${account.id}" style="text-decoration: none">
                                        <c:out value="${account.lastName}"></c:out>
                                        <c:out value="${account.firstName}"></c:out>
                                    </a>
                                </div>
                            </div>
                            <br>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="w3-col s6">
            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container">
                            <p class="w3-center"><label>Группы</label></p>
                            <hr>
                            <c:if test="${empty groups}">
                                <p class="w3-center">Не найдено</p>
                            </c:if>
                            <c:if test="${not empty groups}">
                                <c:forEach var="group" items="${groups}">
                                    <div class="w3-row">
                                        <div class="w3-col m3">
                                            <a href="/group?id=${group.id}"><img
                                                    src="/getImage?type=group&id=${group.id}"
                                                    alt="Avatar" class="w3-circle"
                                                    style="height:30px;width:30px"></a>
                                        </div>
                                        <div class="w3-col m6">
                                            <a href="/group?id=${group.id}" style="text-decoration: none">
                                                <c:out value="${group.name}"></c:out>
                                            </a>
                                        </div>
                                    </div>
                                    <br>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
