package com.exam.ExamJavaSpring.entyties;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails 
{
    private String id;
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String country;
    private String city;
    private Boolean isAdmin;
    private byte[] accauntImg;

    public static UserDetailsImpl build(User user)
    {
        return new UserDetailsImpl(
            user.getId(), user.getUsername(), user.getPassword(), user.getLastName(), user.getFirstName(), 
            user.getPatronymic(), user.getCountry(), user.getCity(), user.getIsAdmin(), user.getAccauntImg()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Autowired
    public String getId()
    {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
