package com.company.config;

import com.company.domain.Profile;
import com.company.dto.profile.ProfileModuleDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    @Getter
    String id;
    String phone;
    String password;
    Set<GrantedAuthority> authorities;

    public CustomUserDetails(Profile profile) {
        id = profile.getId();
        phone = profile.getPhone();
        password = profile.getPassword();
        authorities = (Set<GrantedAuthority>) mapModulesToAuthorities(profile.getModules());

    }

    // MAPPERS
    public Collection<? extends GrantedAuthority> mapModulesToAuthorities(List<ProfileModuleDto> modules) {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if (modules != null && !modules.isEmpty()) {
            for (ProfileModuleDto module : modules) {
                if (module != null) {
                    for (String permission : module.getPermissions()) {
                        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(String.format("%s:%s", module.getId(), permission)));
                    }
                }
            }
        }
        return simpleGrantedAuthorities;
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
        return phone;
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
}