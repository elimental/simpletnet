<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Участники группы</title>
</head>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
        <table class="w3-table-all w3-hoverable">
            <thead>
            <tr class="w3-light-blue">
                <th>Участники группы</th>
            </tr>
            </thead>
            <c:forEach var="member" items="${adminsAndModerators}">
                <tr>
                    <td><a href="/userProfile?id=${member.id}">${member.firstName} ${member.lastName}</a>
                        <c:if test="${owner && member.id != userId}">
                            <a href="/deleteFromGroup?userId=${member.id}&groupId=${groupId}" class="w3-text-green">Удалить из группы</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:forEach var="member" items="${simpleMembers}">
                <tr>
                    <td><a href="/userProfile?id=${member.id}">${member.firstName} ${member.lastName}</a>
                        <c:if test="${delete}">
                            <a href="/deleteFromGroup?userId=${member.id}&groupId=${groupId}" class="w3-text-green">Удалить из группы</a>
                            <a href="/makeModerator?userId=${member.id}&groupId=${groupId}" class="w3-text-green">Сделать модератором</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
</div>
</body>
</html>
