package com.exam.ExamJavaSpring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exam.ExamJavaSpring.entyties.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    
}
