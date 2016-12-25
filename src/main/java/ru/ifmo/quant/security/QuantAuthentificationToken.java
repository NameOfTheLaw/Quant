package ru.ifmo.quant.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by andrey on 25.12.2016.
 */
public class QuantAuthentificationToken extends AbstractAuthenticationToken {

    Object credentials;

    public QuantAuthentificationToken(Object credentials) {
        super(null);
        this.credentials = credentials;
    }

    public QuantAuthentificationToken(Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = credentials;
    }

    public QuantAuthentificationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public Object getCredentials() {
        return credentials;
    }

    public Object getPrincipal() {
        return null;
    }
}
