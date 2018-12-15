<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Чат</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/web_sock_chat.js"></script>
</head>
<body>
<div class="w3-container w3-content" style="max-width:600px;margin-top:80px">
    <div class="w3-row">
        <div class="w3-col m12">
            <div class="w3-card w3-round w3-white">
                <div class="w3-container w3-padding" style="height: 88vh">
                    <c:import charEncoding="utf-8" var="secondTalkerName"
                              url="/getAccountFullName?id=${secondTalkerId}"></c:import>
                    <p class="w3-center"><label>Чат с <c:out value="${secondTalkerName}"></c:out></label></p>
                    <hr>
                    <div class="chat_container" id="chatContainer">
                        <c:forEach var="message" items="${chatMessages}">
                            <div class="w3-container">
                                <img src="/getImage?type=account&id=${message.from.id}" alt="Avatar"
                                     class="w3-circle" style="height:20px;width:20px">
                                <c:import charEncoding="utf-8" var="userName"
                                          url="/getAccountFullName?id=${message.from.id}"></c:import>
                                <label style="font-size: small"><c:out value="${userName}"></c:out></label>
                                <span class="w3-opacity w3-right" style="font-size: x-small"><fmt:formatDate
                                        pattern="dd.MM.yyyy hh.mm"
                                        value="${message.createDate}"/></span>
                                <p style="font-size: small"><c:out value="${message.text}"></c:out></p>
                            </div>
                        </c:forEach>
                    </div>
                    <hr>
                    <div class="w3-cell-row">
                        <div class="w3-cell">
                            <input class="w3-input w3-border" id="messageText" type="text">
                        </div>
                        <div class="w3-cell w3-container">
                            <button class="w3-button w3-theme" type="submit" id="sendMessage">Отправить</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="from" hidden>${userId}</div>
<div id="to" hidden>${secondTalkerId}</div>
</body>
</html>
