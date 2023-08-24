package com.exam.ExamJavaSpring.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class MainController 
{
    @GetMapping("/accaunt")
    public String getAccauntData(Principal principal)
    {
        if(principal == null)
        {
            return null;
        }
        return principal.getName();
    }
}
