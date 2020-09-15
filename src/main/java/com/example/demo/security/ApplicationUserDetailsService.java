package com.example.demo.security;

import com.example.demo.entity.ApplicationUser;
import com.example.demo.services.ApplicationUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final ApplicationUserService service;

    public ApplicationUserDetailsService(ApplicationUserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = service.getByUsername(username);
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return applicationUser.getAuthorities();
            }

            @Override
            public String getPassword() {
                return applicationUser.getPassword();
            }

            @Override
            public String getUsername() {
                return applicationUser.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
