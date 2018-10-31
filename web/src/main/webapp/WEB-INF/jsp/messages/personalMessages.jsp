<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Список чатов</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:400px;margin-top:80px">
    <div class="w3-col m12">
        <c:if test="${empty talkers}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>У Вас пока нет активных чатов</label></p>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty talkers}">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <p class="w3-center"><label>Список собеседников</label></p>
                <hr>
                <c:forEach var="talker" items="${talkers}">
                    <div class="w3-row">
                        <div class="w3-col m3">
                            <c:import charEncoding="utf-8" var="talkerName" url="/getUserName?id=${talker}"></c:import>
                            <a href="/chat?id=${talker}">
                                <img src="/getImage?type=user&id=${talker}" alt="Avatar"
                                     class="w3-circle" style="height:55px;width:55px">
                            </a>
                        </div>
                        <div class="w3-col m6">
                            <h5>
                                <a href="/chat?id=${talker}" style="text-decoration: none">
                                    <c:out value="${talkerName}"></c:out>
                                </a>
                            </h5>
                        </div>
                    </div>
                    <br>
                </c:forEach>
            </div>
            </c:if>
        </div>
    </div>
</body>
</html>
