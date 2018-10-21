package com.getjavajob.simplenet.web.util;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Group;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.GroupService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.*;

import static com.getjavajob.simplenet.common.entity.Phone.HOME;
import static com.getjavajob.simplenet.common.entity.Phone.WORK;
import static com.getjavajob.simplenet.service.PasswordEncryptService.genHash;

public class ServletHelper {

    private static AccountService accountService;
    private static GroupService groupService;
    private Map<String, String> param;
    private List<Phone> phones;
    private byte[] img;
    private HttpServletRequest request;

    static {
        accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountService.class);
        groupService = ApplicationContextProvider.getApplicationContext().getBean(GroupService.class);
    }

    public ServletHelper(HttpServletRequest request) {
        this.request = request;
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

    public static void deleteCookies(HttpServletResponse resp) {
        Cookie emailCookie = new Cookie("email", "");
        emailCookie.setMaxAge(0);
        Cookie passCookie = new Cookie("password", "");
        passCookie.setMaxAge(0);
        resp.addCookie(emailCookie);
        resp.addCookie(passCookie);
    }

    public static void invalidateSession(HttpServletRequest req) {
        req.getSession().invalidate();
    }

    public void createGroup() {
        parsCreateGroupParam();
        Group group = new Group();
        int groupOwner = (Integer) request.getSession().getAttribute("userId");
        group.setOwner(groupOwner);
        Date createDate = new Date(System.currentTimeMillis());
        group.setCreateDate(createDate);
        String groupName = param.get("name");
        group.setName(groupName);
        String description = param.get("description");
        if (!description.equals("")) {
            group.setDescription(description);
        }
        if (img != null) {
            group.setPicture(img);
        }
        groupService.addGroup(group);
    }

    public int updateGroup() {
        parsCreateGroupParam();
        int id = Integer.parseInt(param.get("id"));
        Group group = groupService.getGroupById(id);
        String name = param.get("name");
        group.setName(name);
        String description = param.get("description");
        if (!description.equals("")) {
            group.setDescription(description);
        } else {
            group.setDescription(null);
        }
        if (img != null) {
            group.setPicture(img);
        }
        groupService.updateGroup(group);
        return id;
    }

    public boolean registration() {
        parsUserRegOrUpdateParam();
        String email = param.get("email");
        if (accountService.ifEmailAlreadyPresent(email)) {
            return false;
        }
        Account account = createAccount(true);
        accountService.addAccount(account);
        return true;
    }

    public int editProfile() {
        parsUserRegOrUpdateParam();
        Account account = createAccount(false);
        accountService.updateAccount(account);
        return account.getId();
    }

    private Account createAccount(boolean ifReg) {
        Account account;
        if (ifReg) {
            account = new Account();
            account.setRole(1);
        } else {
            int userId = Integer.parseInt(param.get("id"));
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
        request.getSession().setAttribute("userName", firstName);
        account.setFirstName(firstName);
        String lastName = param.get("last_name");
        if (!lastName.equals("")) {
            account.setLastName(lastName);
        } else {
            account.setLastName(null);
        }
        String patronymicName = param.get("patronymic_name");
        if (!patronymicName.equals("")) {
            account.setPatronymicName(patronymicName);
        } else {
            account.setPatronymicName(null);
        }
        String birthDay = param.get("birthday");
        if (!birthDay.equals("")) {
            account.setBirthDay(Date.valueOf(birthDay));
        } else {
            account.setBirthDay(null);
        }
        String skype = param.get("skype");
        if (!skype.equals("")) {
            account.setSkype(skype);
        } else {
            account.setSkype(null);
        }
        String icq = param.get("icq");
        if (!icq.equals("")) {
            account.setIcq(icq);
        } else {
            account.setIcq(null);
        }
        String additionalInfo = param.get("addinfo");
        if (!additionalInfo.equals("")) {
            account.setAdditionalInfo(additionalInfo);
        } else {
            account.setAdditionalInfo(null);
        }
        if (img != null) {
            account.setPhoto(img);
        }
        if (!phones.isEmpty()) {
            account.setPhones(phones);
        } else {
            account.setPhones(null);
        }
        return account;
    }

    private void parsUserRegOrUpdateParam() {
        List<FileItem> items = getItemList();
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
                getImg(item);
            }
        }
    }

    private void parsCreateGroupParam() {
        List<FileItem> items = getItemList();
        for (FileItem item : items) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue = convertIsoToUTF(item.getString());
                param.put(fieldName, fieldValue);
            } else {
                getImg(item);
            }

        }
    }

    private void getImg(FileItem item) {
        if (!FilenameUtils.getName(item.getName()).equals("")) {
            try (InputStream uploadedFile = item.getInputStream()) {
                img = new byte[uploadedFile.available()];
                uploadedFile.read(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<FileItem> getItemList() {
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
        return items;
    }
}
