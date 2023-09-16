package com.exam.ExamJavaSpring.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.ExamJavaSpring.entyties.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> 
{
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);
}
