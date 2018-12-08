package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.common.entity.WallMessage;
import com.getjavajob.simplenet.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.ADMIN;
import static com.getjavajob.simplenet.web.util.WebUtils.*;

@Controller
@SessionAttributes("userId")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/userProfile")
    public ModelAndView showUserProfile(@SessionAttribute("userId") long userIdInSession, long id) {
        logger.trace("User(id={}) is going to user profile id={}", userIdInSession, id);
        Account account = accountService.getAccountById(id);
        if (account == null) {
            logger.error("User(id={}) is trying to get nonexistent user profile id={}", userIdInSession, id);
            return new ModelAndView("userprofile/unknownAccount");
        }
        ModelAndView modelAndView = new ModelAndView("userprofile/userProfile");
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
            preparePhonesForModel(phones, homePhones, workPhones);
            List<WallMessage> wallMessages = accountService.getWallMessages(id);
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
            logger.error("Registration error. Email address {} is already exist", email);
            return "userprofile/registrationError";
        }
        if (!img.isEmpty()) {
            account.setPhoto(img.getBytes());
        }
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            account.setPhones(removeNullNumbers(phones));
            setPhoneOwner(account.getPhones(), account);
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountService.addAccount(account);
        logger.trace("Registration accepted. {}", email);
        return "userprofile/registrationAccept";
    }

    @GetMapping("/editUserProfile")
    public ModelAndView editUserProfile(@SessionAttribute("userId") long userIdInSession, long id) {
        logger.trace("User(id={}) is going to edit user profile id={}", userIdInSession, id);
        ModelAndView modelAndView = new ModelAndView("userprofile/editUserProfile");
        Account account = accountService.getAccountById(id);
        boolean owner = id == userIdInSession;
        List<Phone> phones = account.getPhones();
        List<Phone> homePhones = new ArrayList<>();
        List<Phone> workPhones = new ArrayList<>();
        preparePhonesForModel(phones, homePhones, workPhones);
        modelAndView.addObject("account", account);
        modelAndView.addObject("homePhones", homePhones);
        modelAndView.addObject("workPhones", workPhones);
        modelAndView.addObject("owner", owner);
        return modelAndView;
    }

    @PostMapping("/checkEditUserProfile")
    public String checkEditUserProfile(@SessionAttribute("userId") long userIdInSession,
                                       @RequestParam("img") MultipartFile img,
                                       @ModelAttribute Account account, HttpSession session) throws IOException {
        Long id = account.getId();
        List<Phone> phones = account.getPhones();
        if (phones != null) {
            account.setPhones(removeNullNumbers(phones));
            setPhoneOwner(phones, account);
        }
        if (!img.isEmpty()) {
            account.setPhoto(img.getBytes());
        }
        if (account.getId().equals(userIdInSession)) {
            session.setAttribute("userName", account.getFirstName());
        }
        accountService.updateAccount(account);
        logger.trace("User(id={}) changed user profile id={}", userIdInSession, id);
        return "redirect:/userProfile?id=" + id;
    }

    @GetMapping("/confirmDeleteUserProfile")
    public String confirmDeleteUserProfile(@SessionAttribute("userId") long userIdInSession, int id, Model model) {
        logger.trace("User(id={}) is trying to delete user profile id={}", userIdInSession, id);
        model.addAttribute("id", id);
        return "userprofile/confirmDeleteUserProfile";
    }

    @GetMapping("/deleteUserProfile")
    public String deleteUserProfile(@SessionAttribute("userId") long userIdInSession, long id,
                                    HttpServletRequest request, SessionStatus status) throws ServletException {
        boolean owner = id == userIdInSession;
        if (owner) {
            accountService.deleteSelfAccount(id);
            logger.trace("User(id={}) deleted selfaccount", id);
            status.setComplete();
            request.logout();
        } else {
            accountService.deleteAccount(id);
            logger.trace("User(id={}) deleted user profile id={}", userIdInSession, id);
        }
        return "userprofile/profileDeleted";
    }

    @GetMapping("/getAccountFullName")
    @ResponseBody
    public String getUserName(Long id) {
        return accountService.getAccountById(id).getAccountFullName();
    }

    @GetMapping("/makeAdmin")
    public String makeAdmin(@SessionAttribute("userId") long userIdInSession, long id) {
        accountService.updateAccountRole(id, ADMIN);
        logger.trace("User(id={}) gave administration role to user profile id={}", userIdInSession, id);
        return "redirect:/userProfile?id=" + id;
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
