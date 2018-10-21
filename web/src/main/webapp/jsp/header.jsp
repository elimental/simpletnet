<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/common.css">
<script src="/js/script.js"></script>
<style>
    html, body, h1, h2, h3, h4, h5 {
        font-family: "Open Sans", sans-serif
    }
</style>
<body class="w3-theme-l5">

<!-- Navbar -->

<div class="w3-top">
    <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
        <a href="/login" class="w3-bar-item w3-button w3-padding-large w3-hover-green"><i
                class="fa fa-home w3-margin-right"></i>SimpleNet</a>
        <form action="/search" method="get">
            <input class="w3-bar-item w3-padding-large w3-hover-white w3-theme" type="text" name="search" placeholder="&#128269">
        </form>
        <a href="/messages" class="w3-bar-item w3-button w3-padding-large w3-hover-green" title="Соощения"><i
                class="fa fa-envelope"></i></a>
        <a href="/friends" class="w3-bar-item w3-button w3-padding-large w3-hover-green" title="Друзья"><i
                class="fa fa-user"></i></a>
        <a href="/groups" class="w3-bar-item w3-button w3-padding-large w3-hover-green" title="Группы"><i
                class="fa fa-globe"></i></a>
        <div class="w3-dropdown-hover w3-hide-small w3-right" style="float:right">
            <button class="w3-button w3-padding-large w3-hover-green" title="My Account"><img
                    src="/pic/avatar_small.png" class="w3-circle" style="height:23px;width:23px" alt="Avatar">
                <c:if test="${not empty sessionScope.userName}">
                    <c:out value="${sessionScope.userName}"/>
                </c:if>
            </button>
            <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="right:0">
                <c:if test="${not empty sessionScope.userId}">
                    <a href="/editProfile?id=${sessionScope.userId}" class="w3-bar-item w3-button">Редактировать аккаунт</a>
                    <a href="/confirmDelete?type=user&id=${sessionScope.userId}" class="w3-bar-item w3-button">Удалить аккаунт</a>
                    <a href="/logout" class="w3-bar-item w3-button">Выход</a>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
