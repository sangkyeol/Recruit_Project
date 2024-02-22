package com.test.recruit.data.dto.security;

import com.test.recruit.data.enumval.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Builder
public class CustomUserDetails implements UserDetails, Serializable {
    private int memberNo;
    private String id;
    private String password;
    private MemberStatus memberStatus;
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return memberStatus == MemberStatus.ALIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return memberStatus == MemberStatus.ALIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return memberStatus == MemberStatus.ALIVE;
    }

}