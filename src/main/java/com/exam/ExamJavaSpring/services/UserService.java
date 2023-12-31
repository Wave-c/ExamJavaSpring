package com.exam.ExamJavaSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.ExamJavaSpring.entyties.User;
import com.exam.ExamJavaSpring.entyties.UserDetailsImpl;
import com.exam.ExamJavaSpring.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService, AutoCloseable
{
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        try
        {
            User user = userRepository.findUserByUsername(username).orElseThrow();
            return UserDetailsImpl.build(user);
        }
        catch(UsernameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        
    }
}
