package com.masterpeace.atmosphere.configuration;

import com.masterpeace.atmosphere.security.CurrentUser;

/**
 *
 */
public interface AtmosphereSecurityConfig {
    CurrentUser currentUser() throws Exception;
}
