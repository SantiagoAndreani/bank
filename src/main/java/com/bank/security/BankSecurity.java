package com.bank.security;

import com.bank.models.UserRole;
import com.bank.services.MyUserDetailsService;
import com.bank.services.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class BankSecurity extends WebSecurityConfigurerAdapter {

    private final String [] STATIC_RESOURCES = new String [] {"/css/**", "/js/**", "/img/**"};

    private PasswordEncoderService encoderService;
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public BankSecurity(PasswordEncoderService encoderService, MyUserDetailsService myUserDetailsService) {
        this.encoderService = encoderService;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(encoderService.bCrypt());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(STATIC_RESOURCES).permitAll()
                .antMatchers("/admin/home").hasRole(UserRole.ADMIN_ROLE.toString())
                .antMatchers("/user/home").hasRole(UserRole.USER_ROLE.toString())
                .antMatchers("/register", "/registerUser").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/index").permitAll()
                .loginProcessingUrl("/index")
                    .defaultSuccessUrl("/home")
                    .failureUrl("/index?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                .logout().permitAll()
                    .logoutSuccessUrl("/index?logout");
    }


}
