package com.exam.ExamJavaSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamJavaSpringApplication {
	private JwtCore jwtCore;

	public void setJwtCore(JwtCore jwtCore)
	{
		this.jwtCore = jwtCore;
	}

	public static void main(String[] args) {
		SpringApplication.run(ExamJavaSpringApplication.class, args);
	}

}
