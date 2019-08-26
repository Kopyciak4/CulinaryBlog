package com.culinaryblog.filters;


import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginAndPasswordRetrieverFilter extends AbstractAuthenticationProcessingFilter {
    public LoginAndPasswordRetrieverFilter(AntPathRequestMatcher url, AuthenticationManager authManager) {
        super(url);
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        System.out.println(12345);
        String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
        String encodedCredentials = authorization.substring("Basic".length()).trim();
        System.out.println(authorization);
        //byte[] decodedCredentials = Base64.getDecoder().decode(encodedCredentials);
        //String credentials = new String(decodedCredentials);
        //String[] data = credentials.split(":");
        String[] data = encodedCredentials.split(":");
        System.out.println(data[0]);
        System.out.println(data[1]);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        data[0],
                        data[1],
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {
    }
}
