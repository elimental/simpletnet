<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Запрещенная операция</title>
</head>
<body>
<div class="w3-container w3-content w3-display-middle">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <div class="w3-container w3-center">
                    <p><label>Ошибка</label><p>
                    <hr>
                    <p>У Вас нет прав для выполнения данной операции<p>
                    <button class="w3-button w3-theme" onclick="goBack()">Назад</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
