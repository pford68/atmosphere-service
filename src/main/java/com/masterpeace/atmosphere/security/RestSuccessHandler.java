package com.masterpeace.atmosphere.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterpeace.atmosphere.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 *
 */
@Component
public class RestSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    private static final Logger LOGGER = Logger.getLogger(RestSuccessHandler.class);

    private final ObjectMapper mapper;

    @Autowired
    public RestSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.mapper = messageConverter.getObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        AtmosphereUserDetails userDetails = (AtmosphereUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        LOGGER.info(userDetails.getUsername() + " has logged in.");

        PrintWriter writer = response.getWriter();
        mapper.writeValue(writer, user);
        writer.flush();
    }
}
