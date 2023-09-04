package com.exam.ExamJavaSpring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamJavaSpring.entyties.Room;
import com.exam.ExamJavaSpring.services.RoomService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/main")
public class MainController 
{
    private RoomService roomService;
    private Gson gson = new Gson();

    @Autowired
    public void setRoomService(RoomService roomService)
    {
        this.roomService = roomService;
    }

    @GetMapping("/get-room-cards-list")
    public ResponseEntity<?> getRoomCardsList(@RequestParam int page)
    {
        System.out.println("get room card list: OK");
        List<Room> rooms = roomService.loadRoomsByPage(page);
        System.out.println("rooms init: OK");
        String jsonRoomsList = gson.toJson(rooms);
        
        return ResponseEntity.ok(jsonRoomsList);
    }
}
