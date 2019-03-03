package com.masterpeace.atmosphere.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by philip on 4/24/15.
 */
public class CurrentUser{
    public String get() throws Exception{
        String userName = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null){
            throw new Exception("The security context is null");
        }
        Authentication auth = context.getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            userName = auth.getName();
        }
        if (userName == null){
            throw new Exception("User not found");
        }
        return userName;
    }
}
