package com.exam.ExamJavaSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.ExamJavaSpring.entyties.communications.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String>
{
    
}
