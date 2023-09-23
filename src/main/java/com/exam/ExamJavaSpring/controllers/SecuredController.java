package com.exam.ExamJavaSpring.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamJavaSpring.entyties.Room;
import com.exam.ExamJavaSpring.entyties.User;
import com.exam.ExamJavaSpring.entyties.communications.Application;
import com.exam.ExamJavaSpring.repositories.UserRepository;
import com.exam.ExamJavaSpring.requests.SetAccauntImgRequest;
import com.exam.ExamJavaSpring.responses.GetApplicationsResponse;
import com.exam.ExamJavaSpring.services.ApplicationService;
import com.exam.ExamJavaSpring.services.RoomService;
import com.exam.ExamJavaSpring.services.UserRoomService;
import com.google.gson.*;

@RestController
@RequestMapping("/secured")
public class SecuredController 
{
    private UserRepository userRepository;
    private UserRoomService userRoomService;
    private RoomService roomService;
    private ApplicationService applicationService;
    private Gson gson = new Gson();

    @Autowired
    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setRoomService(RoomService roomService)
    {
        this.roomService = roomService;
    }

    @Autowired
    public void setUserRoomService(UserRoomService userRoomService)
    {
        this.userRoomService = userRoomService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/accaunt")
    public ResponseEntity<?> getAccauntUsername(Principal principal)
    {
        if(principal == null)
        {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(principal.getName());
    }

    @GetMapping("/get-accaunt-img")
    public ResponseEntity<?> getAccauntImg(@RequestParam String userName)
    {
        Optional<User> user = userRepository.findUserByUsername(userName);
        if(user.isPresent())
        {
            return ResponseEntity.ok(user.get().getAccauntImg());
        }
        else
        {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/set-accaunt-img")
    public ResponseEntity<?> setAccauntImg(@RequestBody SetAccauntImgRequest setAccauntImgRequest)
    {
        try
        {
            User user = userRepository.findUserByUsername(setAccauntImgRequest.getUserName()).get();
            userRepository.delete(user);
            user.setAccauntImg(setAccauntImgRequest.getAccauntImg());
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/get-accaunt-data")
    public ResponseEntity<?> getAccauntData(Principal principal)
    {
        if(principal == null)
        {
            return ResponseEntity.status(401).build();
        }
        else
        {
            User user = userRepository.findUserByUsername(principal.getName()).get();
            Gson gson = new Gson();
            String resp = gson.toJson(user);
            return ResponseEntity.ok(resp);
        }
    }

    @GetMapping("/get-rentaled-rooms")
    public ResponseEntity<?> getRentaledRooms(Principal principal)
    {
        if(principal == null)
        {
            return ResponseEntity.status(401).build();
        }
        else
        {
            String userId = userRepository.findUserByUsername(principal.getName()).get().getId();
            List<String> roomsId = userRoomService.findAllRentalByUserId(UUID.fromString(userId));
            Room[] rooms = new Room[roomsId.size()];
            for (int i = 0; i < roomsId.size(); i++) 
            {
                rooms[i] = roomService.loadRoomById(UUID.fromString(roomsId.get(i)));
            }
            return ResponseEntity.ok().body(gson.toJson(rooms));
        }
    }

    @PostMapping("/to-rent-out")
    public ResponseEntity<?> rentOut(@RequestBody Room addedRoom, Principal principal)
    {
        if(principal == null)
        {
            return ResponseEntity.status(401).build();
        }
        else
        {
            addedRoom.setId(UUID.randomUUID().toString());
            addedRoom.setUserId(userRepository.findUserByUsername(principal.getName()).get().getId());
            addedRoom.setTitleImg("noimg");
            if(roomService.saveRoom(addedRoom) == 0)
            {
                return ResponseEntity.ok("ok");
            }
            else
            {
                return ResponseEntity.status(500).build();
            }
        }
    }

    @PostMapping("/rent-a-room")
    public ResponseEntity<?> rentRoom(@RequestBody Room room, Principal principal)
    {
        if(principal != null)
        {
            User user = userRepository.findUserByUsername(principal.getName()).get();
            if(applicationService.saveApplication(room, user.getId()) == 0)
            {
                return ResponseEntity.ok().build();
            } 
            else
            {
                return ResponseEntity.status(500).build();
            }
        }
        else
        {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/get-applications")
    public ResponseEntity<?> getApplications(Principal principal)
    {
        if(principal != null)
        {
            User user = userRepository.findUserByUsername(principal.getName()).get();
            List<Application> applications = applicationService.findApplicationBySellerId(user.getId());
            List<GetApplicationsResponse> getApplicationsResponses = new ArrayList<>();
            List<Room> rooms = roomService.castListApplicationToListRoom(applications);
            String[] ids = new String[applications.size()];
            for(int i = 0; i < applications.size(); i++)
            {
                ids[i] = applications.get(i).getId();
                getApplicationsResponses.add(new GetApplicationsResponse(rooms.get(i), ids[i]));
            }
            return ResponseEntity.ok(
                gson.toJson(
                    getApplicationsResponses
                )
            );
        }
        else
        {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/access-or-not")
    public ResponseEntity<?> accessOrNot(@RequestParam boolean isAccess, @RequestParam String roomId, @RequestParam String applicationId, Principal principal)
    {
        if(principal != null)
        {
            User user = userRepository.findUserByUsername(principal.getName()).get();
            String buyerId = applicationService.findApplicationById(applicationId).getBuyerId();
            if(isAccess)
            {
                if(userRoomService.saveUserRoom(buyerId, roomId) != 0)
                {
                    return ResponseEntity.status(500).build();
                }
            }
            applicationService.deleteApplicationById(applicationId);
            return ResponseEntity.ok().build();
        }
        else
        {
            return ResponseEntity.status(401).build();
        }
    }
}
