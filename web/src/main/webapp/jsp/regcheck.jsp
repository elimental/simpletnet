<%@ page import="com.getjavajob.simplenet.web.util.JSPHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<%
    if (JSPHelper.parsReg(request)) { %>
<jsp:include page="/jsp/regaccept.jsp"></jsp:include>
<% } else {%>
<jsp:include page="/jsp/regerror.jsp"></jsp:include>
<% }
%>
</body>
</html>
