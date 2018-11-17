<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Группы</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <c:if test="${empty communities}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Вы не состоите ни в одной группе</label></p>
                </div>
            </div>
            <br>
        </c:if>
        <c:if test="${not empty requestedCommunities}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Ваши заявки</label></p>
                    <hr>
                    <c:forEach var="community" items="${requestedCommunities}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <a href="/comminity?id=${community.id}"><img
                                        src="/getImage?type=community&id=${community.id}"
                                        alt="Avatar" class="w3-circle" style="height:55px;width:55px"></a>
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <a href="/community?id=${community.id}" style="text-decoration: none">
                                        <c:out value="${community.name}"></c:out>
                                    </a>
                                </h5>
                            </div>
                            <div class="w3-col m3 w3-center">
                                <a href="/exitFromCommunity?id=${community.id}" class="w3-button w3-theme"
                                   title="Удалить заявку"><i
                                        class="fa fa-close"></i></a>
                            </div>
                        </div>
                        <br>
                    </c:forEach>
                </div>
            </div>
            <br>
        </c:if>
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding w3-content">
                <p>
                <p class="w3-center"><a href="/createCommunity" class="w3-button w3-theme">Создать группу</a></p>
                <p>
            </div>
        </div>
        <br>
        <c:if test="${not empty communities}">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <p class="w3-center"><label>Ваши группы</label></p>
                    <hr>
                    <c:forEach var="community" items="${communities}">
                        <div class="w3-row">
                            <div class="w3-col m3">
                                <a href="/community?id=${community.id}"><img
                                        src="/getImage?type=community&id=${community.id}"
                                        alt="Avatar" class="w3-circle"
                                        style="height:55px;width:55px"></a>
                            </div>
                            <div class="w3-col m6">
                                <h5>
                                    <a href="/community?id=${community.id}" style="text-decoration: none">
                                        <c:out value="${community.name}"></c:out>
                                    </a>
                                </h5>
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
