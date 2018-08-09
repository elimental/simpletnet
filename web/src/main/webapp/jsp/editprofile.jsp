<%@ page import="com.getjavajob.simplenet.common.entity.Account" %>
<%@ page import="com.getjavajob.simplenet.common.entity.Phone" %>
<%@ page import="com.getjavajob.simplenet.dao.AccountDAO" %>
<%@ page import="com.getjavajob.simplenet.dao.PhoneDAO" %>
<%@ page import="com.getjavajob.simplenet.dao.PicturesDAO" %>
<%@ page import="com.getjavajob.simplenet.dao.RelationshipDAO" %>
<%@ page import="com.getjavajob.simplenet.service.AccountService" %>
<%@ page import="java.util.List" %>
<%@ page import="static com.getjavajob.simplenet.common.entity.PhoneType.HOME" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Edit profile</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<script src="/js/script.js"></script>
<script src="/js/edit.js"></script>
<body>
<%
    AccountService accountService = new AccountService(new AccountDAO(), new PhoneDAO(), new RelationshipDAO(), new PicturesDAO());
    int userId = (Integer) session.getAttribute("userId");
    Account account = accountService.getUserById(userId);
    String imgSrc = "/getimage?userId=" + userId;
%>
<br>
<br>
<br>
<div class="w3-container" style="margin: 0 auto; width: 420px">
    <form action="/editprofilecheck" name="register" enctype="multipart/form-data" method="post">
        <label class="w3-text-blue"><b>Photo</b></label>
        <div style="width: 190px">
            <img src=<%=imgSrc%> class="w3-round" style="width: 188px">
        </div>
        <br>
        <input class="w3-input w3-border" type="file" name="photo" accept="image/jpeg" id="photo"
               onchange="uploadPhoto()">
        <%
            String firstName = account.getFirstName();
        %>
        <label class="w3-text-blue"><b>First name</b></label>
        <input class="w3-input w3-border" type="text" name="first_name" value=<%=firstName%> required>

        <%
            String lastName = account.getLastName() == null ? "" : account.getLastName();
        %>
        <label class="w3-text-blue"><b>Last name</b></label>
        <input class="w3-input w3-border" type="text" name="last_name" value="<%=lastName%>">

        <%
            String patronymicName = account.getPatronymicName() == null ? "" : account.getPatronymicName();
        %>
        <label class="w3-text-blue"><b>Patronymic name</b></label>
        <input class="w3-input w3-border" type="text" name="patronymic_name" value="<%=patronymicName%>">

        <%
            String birthDay = account.getBirthDay() == null ? "" : account.getBirthDay().toString();
        %>
        <label class="w3-text-blue"><b>Birthday</b></label>
        <input class="w3-input w3-border" type="date" name="birthday" value="<%=birthDay%>">

        <%
            String skype = account.getSkype() == null ? "" : account.getSkype();
        %>
        <label class="w3-text-blue"><b>Skype</b></label>
        <input class="w3-input w3-border" type="text" name="skype" value=<%=skype%>>

        <%
            String icq = account.getIcq() == null ? "" : account.getIcq();
        %>
        <label class="w3-text-blue"><b>Icq</b></label>
        <input class="w3-input w3-border" type="text" name="icq" value=<%=icq%>>

        <%
            String additionalInfo = account.getAdditionalInfo() == null ? "" : account.getAdditionalInfo();
        %>
        <label class="w3-text-blue"><b>Additional info</b></label>
        <textarea class="w3-input w3-border" rows="5" cols="50" name="addinfo"><%=additionalInfo%></textarea>

        <label class="w3-text-blue"><b>Phones</b></label>
        <div id="phones">
            <%
                List<Phone> phones = account.getPhones();
                if (phones != null) {
                    int i = 0;
                    for (Phone phone : phones) {
                        String phoneParam = "phone" + i;
                        String phoneType = "phone_type" + i;
            %>
            <div class="w3-cell-row">
                <div class="w3-cell">
                    <input class="w3-input w3-border" type="tel" name=<%=phoneParam%> pattern="[0-9]+"
                           value=<%=phone.getNumber()%>>
                </div>
                <div class="w3-container w3-cell">
                    <select class="w3-select w3-border" name=<%=phoneType%>>
                        <%
                            if (phone.getType() == HOME) {
                        %>
                        <option value="home">Home</option>
                        <option value="work">Work</option>
                        <%
                        } else {
                        %>
                        <option value="home">Home</option>
                        <option selected value="work">Work</option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="w3-cell">
                    <button class="w3-btn w3-blue" type="button" onclick="deletePhoneLine(this)">-</button>
                </div>

            </div>
            <%
                        i++;
                    }
                }
            %>
            <button class="w3-btn w3-blue" type="button" onclick="addPhoneLine2(this)">Add phone</button>
        </div>
        <br>
        <button class="w3-btn w3-blue" type="submit">Apply</button>
        <br>
    </form>
</div>
</body>
</html>
