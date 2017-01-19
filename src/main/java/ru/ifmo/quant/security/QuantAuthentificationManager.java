package ru.ifmo.quant.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by andrey on 25.12.2016.
 */
@Component("authentificationManager")
public class QuantAuthentificationManager implements AuthenticationManager, InitializingBean {

    static final List<GrantedAuthority> ADMIN_AUTHORITIES = new ArrayList<GrantedAuthority>();
    static final List<GrantedAuthority> USER_AUTHORITIES = new ArrayList<GrantedAuthority>();
    static final String ADMIN = "ADMIN";
    static final String USER = "USER";
    @Value("#{'${admin.ids}'.split(',')}")
    private Set<Long> adminsIds;

    public void afterPropertiesSet() throws Exception {
        ADMIN_AUTHORITIES.add(new SimpleGrantedAuthority(ADMIN));
        USER_AUTHORITIES.add(new SimpleGrantedAuthority(USER));
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (adminsIds.contains(authentication.getCredentials())) {
            return new QuantAuthentificationToken(authentication.getCredentials(), ADMIN_AUTHORITIES);
        }
        return new QuantAuthentificationToken(authentication.getCredentials(), USER_AUTHORITIES);
    }

    public Set<Long> getAdminsIds() {
        return adminsIds;
    }

    public void setAdminsIds(Set<Long> adminsIds) {
       this.adminsIds = adminsIds;
    }
}
