package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Message;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.service.AccountService;
import com.getjavajob.simplenet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.ADMINISTRATOR;
import static com.getjavajob.simplenet.common.entity.Role.USER;
import static com.getjavajob.simplenet.service.PasswordEncryptService.genHash;
import static com.getjavajob.simplenet.web.util.WebUtils.*;

@Controller
@SessionAttributes("userId")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/userProfile")
    public ModelAndView showUserProfile(@SessionAttribute("userId") int userIdInSession, int id) {
        ModelAndView modelAndView = new ModelAndView("userprofile/userProfile");
        Account account = accountService.getUserById(id);
        boolean owner = id == userIdInSession;
        boolean ifAdminOpens = accountService.ifAdmin(userIdInSession);
        boolean ifFriend = accountService.ifFriend(id, userIdInSession);
        if (!owner && !ifAdminOpens && !ifFriend) {
            ModelAndView accesDenied = new ModelAndView("userprofile/profileAccessDenied");
            boolean alreadyRequested = accountService.ifAlreadyRequested(userIdInSession, id);
            accesDenied.addObject("requestedUserId", id);
            accesDenied.addObject("alreadyRequested", alreadyRequested);
            return accesDenied;
        } else {
            List<Phone> phones = account.getPhones();
            List<Phone> homePhones = new ArrayList<>();
            List<Phone> workPhones = new ArrayList<>();
            preparePhones(phones, homePhones, workPhones);
            List<Message> wallMessages = messageService.getWallMessages(id);
            boolean showMakeAdminButton = !accountService.ifAdmin(id);
            boolean showAdminContent = ifAdminOpens && !owner;
            boolean ifAdmin = accountService.ifAdmin(id);
            boolean showAddFriendButton = !ifFriend;
            modelAndView.addObject("showMakeAdminButton", showMakeAdminButton);
            modelAndView.addObject("showAdminContent", showAdminContent);
            modelAndView.addObject("admin", ifAdmin);
            modelAndView.addObject("showAddFriendButton", showAddFriendButton);
            modelAndView.addObject("owner", owner);
            modelAndView.addObject("account", account);
            modelAndView.addObject("homePhones", homePhones);
            modelAndView.addObject("workPhones", workPhones);
            modelAndView.addObject("wallMessages", wallMessages);
            return modelAndView;
        }
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "userprofile/registration";
    }

    @PostMapping("/checkRegistration")
    public String checkRegistration(@RequestParam("img") MultipartFile img,
                                    @ModelAttribute Account account) throws IOException {
        String email = account.getEmail();
        if (accountService.ifEmailAlreadyPresented(email)) {
            return "userprofile/registrationError";
        }
        if (!img.isEmpty()) {
            account.setPhoto(img.getBytes());
        }
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            account.setPhones(removeNullNumbers(phones));
        }
        account.setRole(USER);
        account.setPassword(genHash(account.getPassword()));
        account.setRegDate(new Date(System.currentTimeMillis()));
        accountService.addAccount(account);
        return "userprofile/registrationAccept";
    }

    @GetMapping("/editUserProfile")
    public ModelAndView editUserProfile(int id) {
        ModelAndView modelAndView = new ModelAndView("userprofile/editUserProfile");
        Account account = accountService.getUserById(id);
        List<Phone> phones = account.getPhones();
        List<Phone> homePhones = new ArrayList<>();
        List<Phone> workPhones = new ArrayList<>();
        preparePhones(phones, homePhones, workPhones);
        modelAndView.addObject("account", accountService.getUserById(id));
        modelAndView.addObject("homePhones", homePhones);
        modelAndView.addObject("workPhones", workPhones);
        return modelAndView;
    }

    @PostMapping("/checkEditUserProfile")
    public String checkEditUserProfile(@RequestParam("img") MultipartFile img,
                                       @ModelAttribute Account account, HttpSession session) throws IOException {
        int id = account.getId();
        Account accountFromDb = accountService.getUserById(id);
        account.setEmail(accountFromDb.getEmail());
        account.setPassword(accountFromDb.getPassword());
        account.setRole(accountFromDb.getRole());
        if (img.isEmpty()) {
            account.setPhoto(accountFromDb.getPhoto());
        } else {
            account.setPhoto(img.getBytes());
        }
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            account.setPhones(removeNullNumbers(phones));
        }
        accountService.updateAccount(account);
        session.setAttribute("userName", account.getFirstName());
        return "redirect:/userProfile?id=" + id;
    }

    @GetMapping("/confirmDeleteUserProfile")
    public String confirmDeleteUserProfile(int id, Model model) {
        model.addAttribute("id", id);
        return "userprofile/confirmDeleteUserProfile";
    }

    @GetMapping("/deleteUserProfile")
    public String deleteUserProfile(@SessionAttribute("userId") int userIdInSession, int id, HttpSession session,
                                    HttpServletResponse response) {
        boolean admin = accountService.ifAdmin(userIdInSession);
        boolean owner = id == userIdInSession;
        boolean allowDeleteUser = admin || owner;
        if (!allowDeleteUser) {
            return "accessDenied";
        } else {
            accountService.deleteAccount(id);
            if (owner) {
                session.removeAttribute("userId");
                deleteCookie(response, "email", "password");
                session.invalidate();
            }
            return "userprofile/profileDeleted";
        }
    }

    @GetMapping("/getUserName")
    @ResponseBody
    public String getUserName(int id) throws IOException {
        Account account = accountService.getUserById(id);
        return makeUserName(account);
    }

    @GetMapping("/makeAdmin")
    public String makeAdmin(@SessionAttribute("userId") int userIdInSession, int id, HttpSession session) {
        boolean admin = accountService.ifAdmin(userIdInSession);
        if (!admin) {
            return "accessDenied";
        }
        accountService.updateUserRole(id, ADMINISTRATOR);
        return "redirect:/userProfile?id=" + id;
    }
}
