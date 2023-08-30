package com.exam.ExamJavaSpring.requests;

import lombok.Data;

@Data
public class RegRequest 
{
    private String username;
    private String password;
    private String last_name;
    private String first_name;
    private String patronymic;
    private String country;
    private String city;
    private Boolean is_admin;
}
