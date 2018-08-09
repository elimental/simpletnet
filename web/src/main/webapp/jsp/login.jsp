<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>SIMPLENET Login</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<%
    if (session != null && session.getAttribute("userId") != null) {
        response.sendRedirect("/jsp/userprofile.jsp");
    } else {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Map<String, String> cookiesMap = new HashMap<>();
            for (Cookie cookie : cookies) {
                cookiesMap.put(cookie.getName(), cookie.getValue());
            }
            String email = cookiesMap.get("email");
            String password = cookiesMap.get("password");
            if (email != null && password != null) {
                response.sendRedirect("/logincheck");
            }
        }
    }
%>
<div class="w3-container w3-display-middle">
    <form action="/logincheck" method="post">

        <label class="w3-text-blue"><b>E-mail</b></label>
        <input class="w3-input w3-border" type="email" name="email" required>

        <label class="w3-text-blue"><b>Password</b></label>
        <input class="w3-input w3-border" type="password" name="password" required><br>

        <button class="w3-btn w3-blue" type="submit">Login</button>
        <br>

        <input class="w3-check" type="checkbox" name="remember">
        <label>Remember me</label>

    </form>
    <a href="/jsp/registration.jsp" class="w3-text-green">Register</a>
</div>
</body>
</html>
