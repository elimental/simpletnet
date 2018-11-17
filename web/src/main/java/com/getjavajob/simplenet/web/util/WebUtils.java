package com.getjavajob.simplenet.web.util;

import com.getjavajob.simplenet.common.entity.Account;
import com.getjavajob.simplenet.common.entity.Phone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.simplenet.common.entity.PhoneType.HOME;


public class WebUtils {

    public static void preparePhonesForModel(List<Phone> phones, List<Phone> homePhones, List<Phone> workPhones) {
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

    public static List<Phone> removeNullNumbers(List<Phone> phones) {
        List<Phone> result = new ArrayList<>();
        for (Phone phone : phones) {
            if (phone.getNumber() != null) {
                result.add(phone);
            }
        }
        return result;
    }

    public static void setPhoneOwner(List<Phone> phones, Account account) {
        for (Phone phone : phones) {
            phone.setPhoneOwner(account);
        }
    }

    public static void deleteCookie(HttpServletResponse response, String... cookies) {
        for (String cookieName : cookies) {
            Cookie cookie = new Cookie(cookieName, "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
