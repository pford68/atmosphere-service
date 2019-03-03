package com.masterpeace.configuration;

import com.masterpeace.atmosphere.configuration.AtmosphereSecurityConfig;
import com.masterpeace.atmosphere.configuration.WebSecurityConfig;
import com.masterpeace.atmosphere.security.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Overrides Spring Security for tests.
 */
@Configuration
public class TestSecurityConfig implements AtmosphereSecurityConfig{


    class CurrentTestUser extends CurrentUser {
        @Override
        public String get(){
            return "pford@gmail.com";
        }
    }

    @Bean
    @Profile("test")
    public CurrentUser currentUser() {
        return new CurrentTestUser();
    }

    @Bean
    public TestingAuthenticationToken testingAuthenticationToken(){
        User user = new User("pford@gmail.com","", AuthorityUtils.createAuthorityList("ROLE_USER"));
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
        return testingAuthenticationToken;
    }
}
