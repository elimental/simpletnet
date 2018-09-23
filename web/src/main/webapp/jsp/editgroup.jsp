<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Редактирование группы</title>
</head>
<script src="/js/script.js"></script>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <form action="/editGroupCheck" name="register" enctype="multipart/form-data" method="post">
        <label class="w3-text-blue"><b>Картинка</b></label>
        <div style="width: 190px">
            <img src="/getImage?type=group&id=${group.id}" class="w3-round" style="width: 188px">
        </div>
        <br>
        <input type="hidden" name="id" value="${group.id}">
        <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img"
               onchange="uploadPhoto()">
        <label class="w3-text-blue"><b>Название</b></label>
        <input class="w3-input w3-border" type="text" name="name" value="${group.name}" required>
        <label class="w3-text-blue"><b>Описание</b></label>
        <input class="w3-input w3-border" type="text" name="description" value="${group.description}">
        <br>
        <button class="w3-btn w3-blue" type="submit">Сохранить</button>
        <br>
    </form>
</div>
</body>
</html>
