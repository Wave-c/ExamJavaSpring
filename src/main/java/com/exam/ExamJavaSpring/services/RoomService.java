package com.exam.ExamJavaSpring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.ExamJavaSpring.entyties.Room;
import com.exam.ExamJavaSpring.repositories.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomService {
    RoomRepository roomRepository;

    @Transactional
    public Room loadRoomById(UUID id)
    {
        try
        {
            return roomRepository.findById(id).orElseThrow();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository)
    {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public List<Room> loadRoomsByPage(int page)
    {
        try
        {
            List<Room> rooms = roomRepository.findAll();
            int size = rooms.size();
            for(int i = page * 30; i < size; i++)
            {
                rooms.remove(30);
                System.out.println("room " + i + " removed!");
            }
            if(page > 1)
            {
                for(int i = 0; i < (page - 1) * 30; i++)
                {
                    rooms.remove(0);
                    System.out.println("room " + i + " removed!");
                }
            }
            return rooms;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
