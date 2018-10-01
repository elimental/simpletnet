<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<html>
<head>
    <title>Новая группа</title>
</head>
<script src="/js/script.js"></script>
<body>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <form action="/createGroupCheck" name="createGroup" enctype="multipart/form-data" method="post">

        <label class="w3-text-blue"><b>Название группы</b></label>
        <input class="w3-input w3-border" type="text" name="name" required>

        <label class="w3-text-blue"><b>Описание</b></label>
        <textarea class="w3-input w3-border" rows="5" cols="50" name="description"></textarea>

        <label class="w3-text-blue"><b>Картинка</b></label>
        <input class="w3-input w3-border" type="file" name="img" accept="image/jpeg" id="img"
               onchange="uploadPhoto()">
        <br>
        <button class="w3-btn w3-blue" type="submit">Создать</button>
        <br>
    </form>
</div>
</body>
</html>
