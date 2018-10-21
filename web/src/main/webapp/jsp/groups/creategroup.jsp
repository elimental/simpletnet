<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Создание группы</title>
</head>
<body>
<div class="w3-container w3-content" style="max-width:450px;margin-top:80px">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <form action="/createGroupCheck" name="createGroup" enctype="multipart/form-data" method="post">

                    <label>Название группы</label>
                    <input class="w3-input w3-border" type="text" name="name" required>

                    <label>Описание</label>
                    <textarea class="w3-input w3-border" rows="5" cols="50" name="description"></textarea>

                    <label>Картинка</label>
                    <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img"
                           onchange="uploadPhoto()">
                    <br>
                    <button class="w3-button w3-theme" type="submit">Создать</button>
                    <br>
                </form>
                <br>
            </div>
        </div>
    </div>
</div>
</body>
</html>

