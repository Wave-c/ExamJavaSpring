package com.exam.ExamJavaSpring.requests;

import lombok.Data;

@Data
public class SigninRequest 
{
    private String username;
    private String password;
}
