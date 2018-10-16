<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Чат</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        label {
            color: dimgray;
        }
    </style>
</head>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <h3 class="w3-text-blue">
        <c:import var="secondTalkerName" url="/getUserName?id=${secondTalkerId}"></c:import>
        Чат с <c:out value="${secondTalkerName}"/>
    </h3>
    <form action="/sendPersonalMessage" method="get">
        <input type="hidden" name="secondTalkerId" value="${secondTalkerId}">
        <textarea class="w3-input w3-border" rows="2" cols="50" name="chatMessage"></textarea>
        <button class="w3-btn w3-blue" type="submit">Отправить</button>
    </form>
    <br>
    <c:if test="${not empty chatMessages}">
        <c:forEach var="message" items="${chatMessages}">
            <c:import var="userName" url="/getUserName?id=${message.author}"></c:import>
            <b class="w3-text-blue">${userName} &#160 </b>
            <label style="font-size: smaller"><fmt:formatDate pattern="dd.MM.yyyy hh.mm"
                                                              value="${message.createDate}"/></label>
            <p><c:out value="${message.text}"/></p>
            <br>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
