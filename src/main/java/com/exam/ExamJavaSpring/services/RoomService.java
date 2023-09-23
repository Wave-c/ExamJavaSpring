package com.exam.ExamJavaSpring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.ExamJavaSpring.entyties.Room;
import com.exam.ExamJavaSpring.entyties.User;
import com.exam.ExamJavaSpring.entyties.communications.Application;
import com.exam.ExamJavaSpring.repositories.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomService {
    RoomRepository roomRepository;

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository)
    {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public int saveRoom(Room room)
    {
        try
        {
            roomRepository.save(room);
            return 0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    @Transactional
    public Room loadRoomById(UUID id)
    {
        try
        {
            return roomRepository.findById(id.toString()).orElseThrow();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
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
            }
            if(page > 1)
            {
                for(int i = 0; i < (page - 1) * 30; i++)
                {
                    rooms.remove(0);
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

    @Transactional
    public List<Room> processingRoomsList(List<Room> rooms, User user)
    {
        for(int i = 0; i < rooms.size(); i++)
        {
            if(rooms.get(i).getUserId().equals(user.getId()))
            {
                rooms.remove(i);
                i--;
            }
        }
        return rooms;
    }

    @Transactional
    public List<Room> castListApplicationToListRoom(List<Application> applications)
    {
        List<Room> rooms = new ArrayList();
        for(Application application : applications)
        {
            rooms.add(roomRepository.findById(application.getRoomId()).get());
        }
        return rooms;
    }
}
