<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Группы</title>
</head>
<body>
<br>
<br>
<br>
<div class="w3-margin-left">
    <a href="/createGroup" class="w3-button w3-blue">Создать группу</a>
    <br>
    <br>
    <c:forEach var="group" items="${groups}">
        <img src="/getImage?type=group&id=${group.id}" class="w3-round" style="width: 120px">
        <a href="/group?id=${group.id}">${group.name}</a><br>
    </c:forEach>
</div>
</body>
</html>
