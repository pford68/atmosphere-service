package com.masterpeace.atmosphere.security;


import com.masterpeace.atmosphere.model.User;
import com.masterpeace.atmosphere.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service
@Qualifier("atmosphereUserDetailsService")
public class AtmosphereUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtmosphereUserDetailsService.class);

    private final UserService userLookupService;


    @Autowired
    public AtmosphereUserDetailsService(UserService userLookupService){
        this.userLookupService = userLookupService;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        try {
            user = this.userLookupService.getUserByUserName(s);
            if (user == null){
                throw new Exception("User not found");
            }
        } catch (Exception e){
            LOGGER.error("The following error occurred when looking up the user:  " + e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
        return new AtmosphereUserDetails(user);
    }

}