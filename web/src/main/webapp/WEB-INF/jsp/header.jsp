<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- W3 CSS -->

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- jQuery -->

<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/Smoothness/jquery-ui.css">
<script
        src="https://code.jquery.com/jquery-3.3.1.js"
        integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
        crossorigin="anonymous"></script>

<!-- jQueryUI -->

<script
        src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"
        integrity="sha256-T0Vest3yCU7pafRw9r+settMBX6JkKN06dqBnpQ8d30="
        crossorigin="anonymous"></script>

<!-- jQuery Validation plugin -->

<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/jquery.validate.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/additional-methods.js"></script>

<!-- Main -->

<link rel="stylesheet" href="/css/common.css">
<script src="/js/script.js"></script>
<body class="w3-theme-l5">

<!-- Navbar -->

<div class="w3-top">
    <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
        <a href="/login" class="w3-bar-item w3-button w3-padding-large w3-hover-green"><i
                class="fa fa-home w3-margin-right"></i>SimpleNet</a>
        <form action="/search" method="get">
            <input class="w3-bar-item w3-padding-large w3-hover-white w3-theme" type="text" name="search"
                   placeholder="&#128269">
        </form>
        <a href="/messages" class="w3-bar-item w3-button w3-padding-large w3-hover-green" title="Соощения"><i
                class="fa fa-envelope"></i></a>
        <a href="/friends" class="w3-bar-item w3-button w3-padding-large w3-hover-green" title="Друзья"><i
                class="fa fa-user"></i></a>
        <a href="/communities" class="w3-bar-item w3-button w3-padding-large w3-hover-green" title="Группы"><i
                class="fa fa-globe"></i></a>
        <div class="w3-dropdown-hover w3-hide-small w3-right" style="float:right">
            <button class="w3-button w3-padding-large w3-hover-green" title="My Account"><img
                    src="/images/avatar_small.png" class="w3-circle" style="height:23px;width:23px" alt="Avatar">
                <c:if test="${not empty sessionScope.userName}">
                    <c:out value="${sessionScope.userName}"/>
                </c:if>
            </button>
            <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="right:0">
                <c:if test="${not empty sessionScope.userId}">
                    <a href="/editUserProfile?id=${sessionScope.userId}" class="w3-bar-item w3-button">Редактировать
                        аккаунт</a>
                    <a href="/confirmDeleteUserProfile?id=${sessionScope.userId}" class="w3-bar-item w3-button">Удалить
                        аккаунт</a>
                    <form action="/logout?${_csrf.parameterName}=${_csrf.token}" method="post">
                        <button type="submit" class="w3-bar-item w3-button">Выход</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
