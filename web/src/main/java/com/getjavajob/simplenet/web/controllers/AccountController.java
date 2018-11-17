package com.getjavajob.simplenet.web.controllers;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;
import com.getjavajob.simplenet.common.entity.WallMessage;
import com.getjavajob.simplenet.service.AccountService;
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
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.Role.ADMINISTRATOR;
import static com.getjavajob.simplenet.web.util.WebUtils.*;

@Controller
@SessionAttributes("userId")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/userProfile")
    public ModelAndView showUserProfile(@SessionAttribute("userId") long userIdInSession, long id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
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
        // todo  sql date ??    done
        // todo  hotkey idea    done
        // todo js deprecated   done
        accountService.addAccount(account);
        return "userprofile/registrationAccept";
    }

    @GetMapping("/editUserProfile")
    public ModelAndView editUserProfile(long id) {
        ModelAndView modelAndView = new ModelAndView("userprofile/editUserProfile");
        Account account = accountService.getAccountById(id);
        List<Phone> phones = account.getPhones();
        List<Phone> homePhones = new ArrayList<>();
        List<Phone> workPhones = new ArrayList<>();
        preparePhonesForModel(phones, homePhones, workPhones);
        modelAndView.addObject("account", account);
        modelAndView.addObject("homePhones", homePhones);
        modelAndView.addObject("workPhones", workPhones);
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
        return "redirect:/userProfile?id=" + id;
    }

    @GetMapping("/confirmDeleteUserProfile")
    public String confirmDeleteUserProfile(int id, Model model) {
        model.addAttribute("id", id);
        return "userprofile/confirmDeleteUserProfile";
    }

    @GetMapping("/deleteUserProfile")
    public String deleteUserProfile(@SessionAttribute("userId") long userIdInSession, long id, HttpSession session,
                                    HttpServletResponse response, SessionStatus status) {
        boolean admin = accountService.ifAdmin(userIdInSession);
        boolean owner = id == userIdInSession;
        boolean allowDeleteUser = admin || owner;
        if (!allowDeleteUser) {
            return "accessDenied";
        } else {
            accountService.deleteAccount(id);
            if (owner) {
                status.setComplete();
                deleteCookie(response, "email", "password");
                session.invalidate();
            }
            return "userprofile/profileDeleted";
        }
    }

    @GetMapping("/getAccountFullName")
    @ResponseBody
    public String getUserName(Long id) {
        return accountService.getAccountById(id).getAccountFullName();
    }

    @GetMapping("/makeAdmin")
    public String makeAdmin(@SessionAttribute("userId") long userIdInSession, long id) {
        boolean admin = accountService.ifAdmin(userIdInSession);
        if (!admin) {
            return "accessDenied";
        }
        accountService.updateAccountRole(id, ADMINISTRATOR);
        return "redirect:/userProfile?id=" + id;
    }
}
