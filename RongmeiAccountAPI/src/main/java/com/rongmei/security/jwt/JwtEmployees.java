package com.rongmei.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtEmployees implements UserDetails {
    private  String username;
    private  String password;
    private Collection<JwtItem> authorities;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;

    public JwtEmployees() {
    }

    public JwtEmployees(String username, String password, Collection<JwtItem> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
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

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<JwtItem> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<JwtItem> authorities) {
        this.authorities = authorities;
    }
}
