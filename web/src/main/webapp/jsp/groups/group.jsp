<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${group.name}</title>
    <style>
        label {
            color: dimgray;
        }
    </style>
</head>
<body>
<div class="w3-cell-row" style="width: 860px">
    <div class="w3-container w3-cell" style="width: 190px">
        <br>
        <br>
        <br>
        <img src="/getImage?type=group&id=${group.id}" class="w3-round" style="width: 188px">
    </div>
    <div class="w3-container w3-cell" style="width: 670px">
        <br>
        <br>
        <div class="w3-container">
            <h3 class="w3-text-blue">
                <c:out value="${group.name}"/>
            </h3>
            <c:if test="${not empty group.description}">
                <label>Описание:</label><c:out value="${group.description}"/><br>
            </c:if>
            <label>Дата cоздания: </label><fmt:formatDate pattern="dd.MM.yyyy" value="${group.createDate}"/>
            <br>
            <br>
            <c:if test="${edit}">
                <a href="/editGroup?id=${group.id}" class="w3-button w3-blue">Редактировать группу</a>
                <br>
                <br>
            </c:if>
            <c:if test="${delete}">
                <a href="/confirmDelete?type=group&id=${group.id}" class="w3-button w3-blue">Удалить группу</a>
                <br>
                <br>
            </c:if>
            <c:if test="${showMakeRequestButton}">
                <a href="/groupRequest?=${group.id}" class="w3-button w3-blue">Стать участником</a>
                <br>
                <br>
            </c:if>
            <a href="/groupMembers?groupId=${group.id}" class="w3-button w3-blue">Список участников</a>
            <br>
            <br>
            <c:if test="${not owner}">
                <a href="/exitFromGroup?groupId=${group.id}" class="w3-button w3-blue">Выйти из группы</a>
            </c:if>
            <br>
            <br>
            <br>
            <br>
            <br>
            <c:if test="${not empty candidates}">
                <table class="w3-table-all w3-hoverable">
                    <thead>
                    <tr class="w3-light-blue">
                        <th>Запросы от пользователей</th>
                    </tr>
                    </thead>
                    <c:forEach var="candidate" items="${candidates}">
                        <tr>
                            <td>${candidate.firstName} ${candidate.lastName}
                                <a href="/acceptGroupRequest?userId=${candidate.id}&groupId=${group.id}" class="w3-text-green">Принять запрос</a>
                                <a href="/rejectGroupRequest?userId=${candidate.id}&groupId=${group.id}" class="w3-text-green">Отклонить</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
