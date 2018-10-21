<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Удаление группы</title>
</head>
<body>
<div class="w3-container w3-content w3-display-middle">
    <div class="w3-col m12">
        <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
                <div class="w3-container w3-center">
                    <p><label>Подтверждение</label><p>
                    <hr>
                    <p>Вы уверены что хотите удалить эту группу<p>
                    <a href="/delete?type=group&id=${id}" class="w3-button w3-theme"><i class="fa fa-check"></i></a>
                    <button class="w3-button w3-theme" onclick="goBack()"><i class="fa fa-close"></i></button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
