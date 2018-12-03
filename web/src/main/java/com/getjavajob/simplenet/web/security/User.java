package com.getjavajob.simplenet.web.security;

import com.getjavajob.simplenet.common.entity.Account;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
class User implements UserDetails {

    private List<Authority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    static UserDetails getUser(Account account) {
        User user = new User();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(account.getRole()));
        user.setAuthorities(authorities);
        user.setUsername(account.getEmail());
        user.setPassword(account.getPassword());
        return user;
    }
}
