package com.exam.ExamJavaSpring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.ExamJavaSpring.entyties.communications.UserRoom;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, String>
{
    
}
