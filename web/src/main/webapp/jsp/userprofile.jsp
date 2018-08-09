<%@ page import="com.getjavajob.simplenet.common.entity.Account" %>
<%@ page import="com.getjavajob.simplenet.common.entity.Phone" %>
<%@ page import="com.getjavajob.simplenet.common.entity.PhoneType" %>
<%@ page import="com.getjavajob.simplenet.dao.AccountDAO" %>
<%@ page import="com.getjavajob.simplenet.dao.PhoneDAO" %>
<%@ page import="com.getjavajob.simplenet.dao.PicturesDAO" %>
<%@ page import="com.getjavajob.simplenet.dao.RelationshipDAO" %>
<%@ page import="com.getjavajob.simplenet.service.AccountService" %>
<%@ page import="com.getjavajob.simplenet.web.util.JSPHelper" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>User profile</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        label {
            color: dimgray;
        }
    </style>
</head>
<body>
<%
    AccountService accountService = new AccountService(new AccountDAO(), new PhoneDAO(), new RelationshipDAO(), new PicturesDAO());
    int userId = (Integer) session.getAttribute("userId");
    Account account = accountService.getUserById(userId);
    String imgSrc = "/getimage?userId=" + userId;
%>
<div class="w3-cell-row" style="width: 860px">
    <div class="w3-container w3-cell" style="width: 190px">
        <br>
        <br>
        <br>
        <img src=<%=imgSrc%> class="w3-round" style="width: 188px">
    </div>
    <div class="w3-container w3-cell" style="width: 670px">
        <br>
        <br>
        <div class="w3-container">
            <h3 class="w3-text-blue"><%=JSPHelper.getFullUserName(account)%>
            </h3>
            <%if (account.getBirthDay() != null) {%>
            <label>Birthday: </label><%=account.getBirthDay()%><br>
            <%}%>
            <%if (account.getIcq() != null) {%>
            <label>Icq: </label><%=account.getIcq()%><br>
            <%}%>
            <%if (account.getSkype() != null) {%>
            <label>Skype: </label><%=account.getSkype()%><br>
            <%}%>
            <%if (account.getAdditionalInfo() != null) {%>
            <label>Additional info: </label><%=account.getAdditionalInfo()%><br>
            <%}%>
            <%
                List<Phone> phones = account.getPhones();
                if (phones != null) {
                    for (Phone phone : phones) {
                        if (phone.getType() == PhoneType.HOME) {
            %>
            <label>Home phone: </label><%=phone.getNumber()%><br>
            <%
                    }
                }
                for (Phone phone : phones) {
                    if (phone.getType() == PhoneType.WORK) {
            %>
            <label>Work phone: </label><%=phone.getNumber()%><br>
            <%
                        }
                    }
                }
            %>
            <label>Registration date: </label><%=account.getRegDate()%>
            <br>
            <a href="/jsp/editprofile.jsp" class="w3-text-green">Edit profile</a>
        </div>

    </div>
</div>
</body>
</html>
