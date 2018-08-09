<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>SIMPLENET Login</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="w3-display-container w3-display-middle">
    <label class="w3-text-red">Invalid login or password</label><br>
    <form action="/logincheck" method="post">

        <label class="w3-text-blue"><b>E-mail</b></label>
        <input class="w3-input w3-border" type="email" name="email" required>

        <label class="w3-text-blue"><b>Password</b></label>
        <input class="w3-input w3-border" type="password" name="password" required><br>

        <button class="w3-btn w3-blue" type="submit">Login</button>
        <br>

        <input class="w3-check" type="checkbox">
        <label>Remember me</label>

    </form>
    <a href="/jsp/registration.jsp" class="w3-text-green">Register</a>
</div>
</body>
</html>