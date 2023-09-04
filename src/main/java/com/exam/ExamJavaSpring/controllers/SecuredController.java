package com.exam.ExamJavaSpring.controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamJavaSpring.entyties.User;
import com.exam.ExamJavaSpring.repositories.UserRepository;
import com.exam.ExamJavaSpring.requests.SetAccauntImgRequest;

import com.google.gson.*;

@RestController
@RequestMapping("/secured")
public class SecuredController 
{
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/accaunt")
    public String getAccauntUsername(Principal principal)
    {
        if(principal == null)
        {
            return null;
        }
        return principal.getName();
    }

    @PostMapping("/get-accaunt-img")
    public String getAccauntImg(@RequestParam(required = true) String userName)
    {
        System.out.println("accauntImg: OK");
        Optional<User> user = userRepository.findUserByUsername(userName);
        if(user.isPresent())
        {
            System.out.println("user is finded");
            return user.get().getAccauntImg();
        }
        else
        {
            return null;
        }
    }

    // @PostMapping("/set-accaunt-img")
    // public ResponseEntity<?> setAccauntImg(@RequestBody Object img, @RequestBody String userName)
    // {
    //     // try
    //     // {
    //     //     Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //     //     User user = userRepository.findUserByUsername(userName).get();
    //     //     userRepository.delete(user);
    //     //     user.setAccauntImg(gson.toJson(img));
    //     //     userRepository.save(user);
    //     //     return ResponseEntity.ok("ёбаный рот, оно работает");
    //     // }
    //     // catch(Exception e)
    //     // {
    //     //     e.printStackTrace();
    //     //     return ResponseEntity.badRequest().body("hui tam");
    //     // }
    // }

    @GetMapping("/get-accaunt-data")
    public ResponseEntity<?> getAccauntData(Principal principal)
    {
        if(principal == null)
        {
            return ResponseEntity.badRequest().body("hui tam");
        }
        else
        {
            User user = userRepository.findUserByUsername(principal.getName()).get();
            Gson gson = new Gson();
            String resp = gson.toJson(user);
            return ResponseEntity.ok(resp);
        }
    }
}
