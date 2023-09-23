package com.exam.ExamJavaSpring;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.exam.ExamJavaSpring.controllers.SecuredController;

@SpringBootApplication
@ComponentScan(basePackageClasses = SecuredController.class)
@ComponentScan(basePackageClasses = JwtCore.class)
public class ExamJavaSpringApplication extends SpringBootServletInitializer{
	private JwtCore jwtCore;

	public void setJwtCore(JwtCore jwtCore)
	{
		this.jwtCore = jwtCore;
	}

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		//new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		SpringApplication.run(ExamJavaSpringApplication.class, args);
		//new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}

}
