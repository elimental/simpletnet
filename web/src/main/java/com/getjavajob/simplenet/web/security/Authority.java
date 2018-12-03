package com.getjavajob.simplenet.web.security;

import com.getjavajob.simplenet.common.entity.Role;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    @Setter
    private Role authority;

    Authority(Role authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }
}
