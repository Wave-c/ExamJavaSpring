package com.exam.ExamJavaSpring.requests;

import lombok.Data;

@Data
public class RegRequest 
{
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String country;
    private String city;
    private Boolean isAdmin;
}
