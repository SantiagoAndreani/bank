package com.bank.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class BankSecurity extends WebSecurityConfigurerAdapter {

    private final String [] STATIC_RESOURCES = new String [] {"/css/**", "/js/**", "/img/**"};


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(STATIC_RESOURCES).permitAll()
                .antMatchers("/register", "/registerUser").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/index").permitAll()
                    .defaultSuccessUrl("/home")
                    .failureUrl("/index?error=true")
                    .usernameParameter("dni")
                    .passwordParameter("password")
                    .and()
                .logout().permitAll()
                    .logoutSuccessUrl("/index?logout");
    }
}