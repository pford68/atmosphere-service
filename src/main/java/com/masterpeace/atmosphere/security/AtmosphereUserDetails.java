package com.masterpeace.atmosphere.security;

import com.masterpeace.atmosphere.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  Provides the user name and credentials for Spring Security
 */
public class AtmosphereUserDetails implements UserDetails {

    private List<String> authorizations;
    private User user;
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    {
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_USER";
            }
        });
    }

    public AtmosphereUserDetails(User user) {
        this.user = user;
    }

    public List<String> getAuthorizations() {
        return this.authorizations;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    public User getUser(){
        return this.user;
    }
}
