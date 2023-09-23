package com.exam.ExamJavaSpring.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamJavaSpring.entyties.Room;
import com.exam.ExamJavaSpring.entyties.User;
import com.exam.ExamJavaSpring.repositories.UserRepository;
import com.exam.ExamJavaSpring.services.ApplicationService;
import com.exam.ExamJavaSpring.services.RoomService;
import com.exam.ExamJavaSpring.services.UserService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/main")
public class MainController 
{
    private RoomService roomService;
    private ApplicationService applicationService;
    private Gson gson = new Gson();
    private UserRepository userRepository;

    @Autowired
    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoomService(RoomService roomService)
    {
        this.roomService = roomService;
    }

    @GetMapping("/get-room-cards-list")
    public ResponseEntity<?> getRoomCardsList(Principal principal, @RequestParam int page) throws Exception
    {
        List<Room> rooms = roomService.loadRoomsByPage(page);
        
        if(principal == null)
        {
            return ResponseEntity.ok(gson.toJson(rooms));
        }
        else
        {
            User user = userRepository.findUserByUsername(principal.getName()).get();
            roomService.processingRoomsList(rooms, user);
            return ResponseEntity.ok(gson.toJson(rooms));
        }
    }
}
