package com.exam.ExamJavaSpring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.engine.spi.ExecutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.exam.ExamJavaSpring.entyties.communications.UserRoom;
import com.exam.ExamJavaSpring.repositories.UserRoomRepository;

import jakarta.transaction.Transactional;

@Service
public class UserRoomService 
{
    private UserRoomRepository userRoomRepository;

    @Autowired
    public void setUserRoomRepository(UserRoomRepository userRoomRepository)
    {
        this.userRoomRepository = userRoomRepository;
    }

    @Transactional
    public List<String> findAllRentalByUserId(UUID userId)
    {
        List<UserRoom> findedUserRoom = userRoomRepository.findAll();
        List<String> roomsId = new ArrayList<String>();

        for (UserRoom item : findedUserRoom) 
        {
            if(item.getUserId().equals(userId.toString()))
            {
                roomsId.add(item.getRoomId());
            }
        }
        return roomsId;
    }

    @Transactional
    public int saveUserRoom(String userId, String roomId)
    {
        try
        {
            userRoomRepository.save(new UserRoom(userId, roomId));
            return 0;
        }
        catch(Exception e)
        {
            return -1;
        }
    }
}
