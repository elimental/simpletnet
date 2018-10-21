<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Редактирование группы</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <form action="/editGroupCheck" name="register" enctype="multipart/form-data" method="post">
                    <p>
                    <div class="w3-container w3-content" style="width: 190px">
                        <img src="/getImage?type=group&id=${group.id}" class="w3-circle" style="width: 180px">
                    </div>
                    <br>
                    <label>Картинка</label>
                    <input type="hidden" name="id" value="${group.id}">
                    <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img"
                           onchange="uploadPhoto()">
                    <label>Название</label>
                    <input class="w3-input w3-border" type="text" name="name" value="${group.name}" required>
                    <label>Описание</label>
                    <input class="w3-input w3-border" type="text" name="description" value="${group.description}">
                    <br>
                    <button class="w3-button w3-theme" type="button" onclick="confirmEdit(this)">Сохранить</button>
                    <br>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

