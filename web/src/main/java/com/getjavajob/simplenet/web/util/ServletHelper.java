package com.getjavajob.simplenet.web.util;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.service.AccountService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.*;

import static com.getjavajob.simplenet.common.entity.PhoneType.HOME;
import static com.getjavajob.simplenet.common.entity.PhoneType.WORK;
import static com.getjavajob.simplenet.service.PasswordEncryptService.genHash;

public class ServletHelper {
    private AccountService accountService = new AccountService();
    private Map<String, String> param;
    private List<Phone> phones;
    private byte[] photo;
    private HttpServletRequest request;

    public ServletHelper(HttpServletRequest request) {
        this.request = request;
    }

    public boolean registration() {
        parsParam(request);
        String email = param.get("email");
        if (accountService.ifEmailAlreadyPresent(email)) {
            return false;
        }
        Account account = createAccount(true);
        accountService.addAccount(account);
        return true;
    }

    public void editProfile() {
        parsParam(request);
        Account account = createAccount(false);
        accountService.updateAccount(account);
    }

    private Account createAccount(boolean ifReg) {
        Account account;
        if (ifReg) {
            account = new Account();
        } else {
            int userId = (Integer) request.getSession().getAttribute("userId");
            account = accountService.getUserById(userId);
        }
        if (ifReg) {
            String email = param.get("email");
            account.setEmail(email);
            String passHash = genHash(param.get("password"));
            account.setPassHash(passHash);
            Date regDate = new Date(System.currentTimeMillis());
            account.setRegDate(regDate);
        }
        String firstName = param.get("first_name");
        account.setFirstName(firstName);
        String lastName = param.get("last_name");
        if (!lastName.equals("")) {
            account.setLastName(lastName);
        }
        String patronymicName = param.get("patronymic_name");
        if (!patronymicName.equals("")) {
            account.setPatronymicName(patronymicName);
        }
        String birthDay = param.get("birthday");
        if (!birthDay.equals("")) {
            account.setBirthDay(Date.valueOf(birthDay));
        }
        String skype = param.get("skype");
        if (!skype.equals("")) {
            account.setSkype(skype);
        }
        String icq = param.get("icq");
        if (!icq.equals("")) {
            account.setIcq(icq);
        }
        String additionalInfo = param.get("addinfo");
        if (!additionalInfo.equals("")) {
            account.setAdditionalInfo(additionalInfo);
        }
        if (photo != null) {
            account.setPhoto(photo);
        }
        if (!phones.isEmpty()) {
            account.setPhones(phones);
        } else {
            account.setPhones(null);
        }
        return account;
    }

    private void parsParam(HttpServletRequest request) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        param = new HashMap<>();
        phones = new ArrayList<>();
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue = convertIsoToUTF(item.getString());
                if (fieldName.equals("phone") && !fieldValue.trim().equals("")) {
                    Phone phone = new Phone();
                    phone.setNumber(fieldValue);
                    item = iterator.next();
                    String phoneType = item.getString();
                    if (phoneType.equals("home")) {
                        phone.setType(HOME);
                    } else {
                        phone.setType(WORK);
                    }
                    phones.add(phone);
                }
                param.put(fieldName, fieldValue);
            } else {
                if (!FilenameUtils.getName(item.getName()).equals("")) {
                    try (InputStream uploadedFile = item.getInputStream()) {
                        photo = new byte[uploadedFile.available()];
                        uploadedFile.read(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static String convertIsoToUTF(String s) {
        String result = "";
        try {
            result = new String(s.getBytes("ISO-8859-1"), Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void preparePhones(List<Phone> phones, List<Phone> homePhones, List<Phone> workPhones) {
        if (phones != null) {
            for (Phone phone : phones) {
                if (phone.getType() == HOME) {
                    homePhones.add(phone);
                } else {
                    workPhones.add(phone);
                }
            }
        }
    }
}
