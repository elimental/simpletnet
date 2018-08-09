package com.getjavajob.simplenet.web.util;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.common.entity.Picture;
import com.getjavajob.simplenet.dao.AccountDAO;
import com.getjavajob.simplenet.dao.PhoneDAO;
import com.getjavajob.simplenet.dao.PicturesDAO;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.getjavajob.simplenet.common.entity.PhoneType.HOME;
import static com.getjavajob.simplenet.common.entity.PhoneType.WORK;
import static com.getjavajob.simplenet.service.PasswordEncryptService.genHash;

public class JSPHelper {
    private AccountService accountService;
    private Map<String, String> param;
    private Picture picture;
    private HttpServletRequest request;

    public JSPHelper(HttpServletRequest request) {
        this.accountService = new AccountService(new AccountDAO(), new PhoneDAO(), new PicturesDAO());
        this.request = request;
    }

    public static String getFullUserName(Account account) {
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String patronymicName = account.getPatronymicName();
        String fullUserName = firstName + (patronymicName == null ? "" : " " + patronymicName) +
                (lastName == null ? "" : " " + lastName);
        return fullUserName;
    }

    public boolean registration() {
        parsParam(request);
        String email = param.get("email");
        if (accountService.ifEmailAlreadyPresent(email)) {
            return false;
        }
        Account account = createAccount(true);
        int userId = accountService.addAccount(account);
        createPicture(true, userId);
        return true;
    }

    public void editProfile() {
        parsParam(request);
        Account account = createAccount(false);
        accountService.updateAccount(account);
        int userId = account.getId();
        createPicture(false, userId);
    }

    private void createPicture(boolean ifReg, int userId) {
        if (picture != null) {
            picture.setUserId(userId);
            if (ifReg) {
                accountService.addPhoto(picture);
            } else {
                accountService.updatePhoto(picture);
            }
        }
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
        List<Phone> phones = new ArrayList<>();
        if (param.get("phone") != null && !param.get("phone").equals("")) {
            Phone phone = new Phone();
            String phoneNumber = param.get("phone");
            phone.setNumber(phoneNumber);
            String phoneType = param.get("phone_type");
            if (phoneType.equals("home")) {
                phone.setType(HOME);
            } else {
                phone.setType(WORK);
            }
            phones.add(phone);
        }
        for (int i = 0; i < 50; i++) {
            String attr = "phone" + i;
            if (param.containsKey(attr)) {
                String phoneNumber = param.get(attr);
                if (!phoneNumber.equals("")) {
                    Phone phone = new Phone();
                    phone.setNumber(phoneNumber);
                    String phoneTypeAttr = "phone_type" + i;
                    String phoneType = param.get(phoneTypeAttr);
                    if (phoneType.equals("home")) {
                        phone.setType(HOME);
                    } else {
                        phone.setType(WORK);
                    }
                    phones.add(phone);
                }
            }
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
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        for (FileItem item : items) {
            if (item.isFormField()) {
                param.put(item.getFieldName(), item.getString());
            } else {
                if (!FilenameUtils.getName(item.getName()).equals("")) {
                    try (InputStream uploadedFile = item.getInputStream()) {
                        picture = new Picture();
                        byte[] fileData = new byte[uploadedFile.available()];
                        uploadedFile.read(fileData);
                        picture.setFileData(fileData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
