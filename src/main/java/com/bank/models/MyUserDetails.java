package com.bank.models;

import com.bank.entities.UserEntity;
import com.bank.entities.UserRoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;
import java.util.stream.Collectors;


public class MyUserDetails implements UserDetails {

    private String dni;
    private String password;
    private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;
    private List<SimpleGrantedAuthority> authorities;

    public MyUserDetails(UserEntity userEntity) {
        dni = userEntity.getDni();
        password = userEntity.getPassword();

        Collection<UserRoleEntity> userRole = userEntity.getRoles();

        accountNonExpired = userRole.stream().map(UserRoleEntity::isAccountNonExpired).findFirst().get();
        accountNonLocked = userRole.stream().map(UserRoleEntity::isAccountNonLocked).findFirst().get();
        credentialsNonExpired = userRole.stream().map(UserRoleEntity::isCredentialsNonExpired).findFirst().get();
        enabled = userRole.stream().map(UserRoleEntity::isEnabled).findFirst().get();
        authorities = userRole.stream()
                .map(userRoleEntity ->
                        new SimpleGrantedAuthority(
                                userRoleEntity.getName().toString())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return dni;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
