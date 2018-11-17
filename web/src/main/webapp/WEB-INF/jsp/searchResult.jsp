<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Результат поиска</title>
    <script src="/js/search.js"></script>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <p class="w3-center"><label>Пользователи</label></p>
                <hr>
                <c:if test="${accountPageQty == 0}">
                    <p class="w3-center">Не найдено</p>
                </c:if>
                <c:if test="${accountPageQty != 0}">
                    <div id="accountSearch">
                        <c:forEach var="searchEntity" items="${accountSearchEntities}">
                            <div class="w3-row">
                                <div class="w3-col m3">
                                    <a href="/userProfile?id=${searchEntity.id}"><img
                                            src="/getImage?type=account&id=${searchEntity.id}"
                                            alt="Avatar" class="w3-circle"
                                            style="height:30px;width:30px"></a>
                                </div>
                                <div class="w3-col m6">
                                    <a href="/userProfile?id=${searchEntity.id}" style="text-decoration: none">
                                        <c:out value="${searchEntity.name}"></c:out>
                                    </a>
                                </div>
                            </div>
                            <br>
                        </c:forEach>
                    </div>
                    <c:if test="${accountPageQty > 1}">
                        <hr>
                        <div class="w3-center">
                            <c:forEach var="index" begin="1" end="${accountPageQty}">
                                <label type="account" class="page">
                                    <a type="button" class="w3-theme" name="${index}">&nbsp;${index}&nbsp;</a></label>
                                &nbsp;
                            </c:forEach>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </div>
        <br>
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <p class="w3-center"><label>Группы</label></p>
                <hr>
                <c:if test="${communityPageQty == 0}">
                    <p class="w3-center">Не найдено</p>
                </c:if>
                <c:if test="${communityPageQty != 0}">
                    <div id="communitySearch">
                        <c:forEach var="searchEntity" items="${communitySearchEntities}">
                            <div class="w3-row">
                                <div class="w3-col m3">
                                    <a href="/community?id=${searchEntity.id}"><img
                                            src="/getImage?type=community&id=${searchEntity.id}"
                                            alt="Avatar" class="w3-circle"
                                            style="height:30px;width:30px"></a>
                                </div>
                                <div class="w3-col m6">
                                    <a href="/community?id=${searchEntity.id}" style="text-decoration: none">
                                        <c:out value="${searchEntity.name}"></c:out>
                                    </a>
                                </div>
                            </div>
                            <br>
                        </c:forEach>
                    </div>
                    <c:if test="${communityPageQty > 1}">
                        <hr>
                        <div class="w3-center">
                            <c:forEach var="index" begin="1" end="${communityPageQty}">
                                <label type="community" class="page">
                                    <a type="button" class="w3-theme" name="${index}">&nbsp;${index}&nbsp;</a></label>
                                &nbsp;
                            </c:forEach>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
</div>
<div id="pattern" hidden>${pattern}</div>
<div id="accountPageQty" hidden>${accountPageQty}</div>
<div id="communityPageQty" hidden>${communityPageQty}</div>
</body>
</html>
