package com.masterpeace.atmosphere.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Enables CORS support to allow cross-origin AJAX requests.  Header values can be set by properties,
 *  such as in an external properties file.
 */
@Component
public class CORSFilter implements Filter {


    @Value("${atmosphere.cors.origin : *}")
    private String origin;


    @Value("${atmosphere.cors.allow-methods : PUT, POST, GET, OPTIONS, DELETE}")
    private String methodsAllowed;


    @Value("${atmosphere.cors.max-age : 3600}")
    private String maxAge;




    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", origin);
        httpResponse.setHeader("Access-Control-Allow-Methods", methodsAllowed);
        httpResponse.setHeader("Access-Control-Max-Age", maxAge);
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
