<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Чат</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:600px;margin-top:80px">
    <div class="w3-row">
        <div class="w3-col m12">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding">
                    <c:import charEncoding="utf-8" var="secondTalkerName" url="/getUserName?id=${secondTalkerId}"></c:import>
                    <p class="w3-center"><label>Чат с <c:out value="${secondTalkerName}"></c:out></label></p>
                    <form action="/sendPersonalMessage" name="chat" method="get">
                        <p contenteditable="true" class="w3-border w3-padding" id="message"></p>
                        <button type="button" class="w3-button w3-theme"
                                onclick="submitPersonalMessageForm(this,${secondTalkerId})">
                            <i class="fa fa-pencil"></i> Отправить
                        </button>
                    </form>
                </div>
            </div>
            <br>
            <div class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                    <c:forEach var="message" items="${chatMessages}">
                        <div class="w3-container w3-padding"><br>
                            <img src="/getImage?type=account&id=${message.author}" alt="Avatar"
                                 class="w3-circle" style="height:30px;width:30px">
                            <c:import charEncoding="utf-8" var="userName" url="/getUserName?id=${message.author}"></c:import>
                            <label style="font-size: small"><c:out value="${userName}"></c:out></label>
                            <span class="w3-opacity w3-right" style="font-size: smaller"><fmt:formatDate
                                    pattern="dd.MM.yyyy hh.mm"
                                    value="${message.createDate}"/></span>
                            <p><c:out value="${message.text}"></c:out></p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
