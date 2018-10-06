<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Чаты</title>
</head>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <c:if test="${empty talkersId}">
        <h3 class="w3-text-blue">У Вас нет активных чатов</h3>
    </c:if>
    <c:if test="${not empty talkersId}">
        <table class="w3-table-all w3-hoverable">
            <thead>
            <tr class="w3-light-blue">
                <th>Чаты</th>
            </tr>
            </thead>
            <c:forEach var="talkerId" items="${talkersId}">
                <tr>
                    <td><c:import var="talkerName" url="/getUserName?id=${talkerId}"></c:import>
                        <a href="/chat?userId=${talkerId}" class="w3-text-green">${talkerName}</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
