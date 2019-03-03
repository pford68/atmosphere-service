package com.masterpeace.atmosphere.configuration;

import com.masterpeace.atmosphere.security.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Overrides Spring Security for development.
 */
@Configuration
@Profile({"dev", "local"})
public class DevSecurityConfig implements AtmosphereSecurityConfig{

    class CurrentDevUser extends CurrentUser {
        @Override
        public String get(){
            return "pford@gmail.com";
        }
    }

    @Bean
    @Profile("dev")
    public CurrentUser currentUser() {
        return new CurrentDevUser();
    }
}
