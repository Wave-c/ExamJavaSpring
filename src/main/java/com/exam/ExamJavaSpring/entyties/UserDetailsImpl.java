package com.exam.ExamJavaSpring.entyties;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

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
    private String accauntImg;

    @Autowired
    public String getId()
    {
        return id;
    }

    @Autowired
    public String getLastName()
    {
        return lastName;
    }

    @Autowired
    public String getFirstName()
    {
        return firstName;
    }

    @Autowired
    public String getPatronymic()
    {
        return patronymic;
    }

    @Autowired
    public String getCountry()
    {
        return country;
    }

    @Autowired
    public String getCity()
    {
        return city;
    }

    @Autowired
    public Boolean getIsAdmin()
    {
        return isAdmin;
    }

    @Autowired
    public String getAccauntImg()
    {
        return accauntImg;
    }

    @Autowired
    public void setId(String id)
    {
        this.id = id;
    }

    @Autowired
    public void setUsername(String username)
    {
        this.username = username;
    }

    @Autowired
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Autowired
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Autowired
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Autowired
    public void setPatronymic(String patronymic)
    {
        this.patronymic = patronymic;
    }

    @Autowired
    public void setCountry(String country)
    {
        this.country = country;
    }

    @Autowired
    public void setCity(String city)
    {
        this.city = city;
    }

    @Autowired
    public void setIsAdmin(Boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    @Autowired
    public void setAccauntImg(String accauntImg)
    {
        this.accauntImg = accauntImg;
    }

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
