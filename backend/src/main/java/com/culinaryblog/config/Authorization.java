package com.culinaryblog.config;


import com.culinaryblog.filters.LoginAndPasswordRetrieverFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Authorization extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    PasswordEncoder passwordEncoder;



    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource).usersByUsernameQuery(
                "SELECT login AS username, password, true FROM user WHERE login =  ?"
        ).authoritiesByUsernameQuery(
                "SELECT login as username, 'ROLE_USER' FROM user WHERE login = ?"
        );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()   .cors().and()
                .authorizeRequests()
                .antMatchers("/user/login", "/user/register", "/recipes", "/recipes/*", "/recipes/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutUrl("/user/logout").logoutSuccessHandler(new LogoutHandler())
                .and().addFilterBefore(new LoginAndPasswordRetrieverFilter(new AntPathRequestMatcher("/user/login", HttpMethod.GET.toString()), authenticationManager()),  UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
    }



}


class LogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {

        if(auth != null && auth.getDetails()!= null){
            req.getSession().invalidate();
        }
        res.setStatus(HttpStatus.OK.value());
    }

}
