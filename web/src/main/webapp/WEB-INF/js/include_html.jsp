<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<!-- Phone line -->
<script type="text/html" id="addPhoneChild">
    <div class="w3-cell-row">
        <div class="w3-cell">
            <input class="phone w3-input w3-border " name="phones[{0}].number">
        </div>
        <div class="w3-container w3-cell">
            <select class="w3-select w3-border" name="phones[{0}].type">
                <option value="HOME">Домашний</option>
                <option value="WORK">Рабочий</option>
            </select>
        </div>
        <div class="w3-cell">
            <button class="w3-button w3-theme delPhoneLine" type="button">-</button>
        </div>
    </div>
</script>
<div hidden id="confirmDialog" title="Редактирование профиля">
    <p><label>Вы хотите сохранить изменения?</label></p>
</div>
<div hidden id="imageDialog" title="Размер файла">
    <p><label>Размер файла не может превышать 5Мб</label></p>
</div>
</body>
</html>
