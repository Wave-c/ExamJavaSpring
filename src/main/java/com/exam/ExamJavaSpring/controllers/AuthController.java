package com.exam.ExamJavaSpring.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.ExamJavaSpring.repositories.UserRepository;
import com.exam.ExamJavaSpring.requests.RegRequest;
import com.exam.ExamJavaSpring.requests.SigninRequest;

import com.exam.ExamJavaSpring.JwtCore;
import com.exam.ExamJavaSpring.entyties.User;

@RestController
@RequestMapping("/auth")
public class AuthController 
{
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder)
    {
        this.encoder = passwordEncoder;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtCore(JwtCore jwtCore)
    {
        this.jwtCore = jwtCore;
    }

    @PostMapping("/registration")
    ResponseEntity<?> registration(@RequestBody RegRequest regRequest)
    {
        if(userRepository.existsByUsername(regRequest.getUsername()))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Выберите другой Login");
        }

        String hashedPassword = encoder.encode(regRequest.getPassword());

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(regRequest.getUsername());
        user.setPassword(hashedPassword);
        user.setFirstName(regRequest.getFirst_name());
        user.setLastName(regRequest.getLast_name());
        user.setPatronymic(regRequest.getPatronymic());
        user.setCountry(regRequest.getCountry());
        user.setCity(regRequest.getCity());
        user.setIsAdmin(false);

        userRepository.save(user);

        return ResponseEntity.ok("ебаный рот, оно зароботало");
    }
    
    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(@RequestBody SigninRequest signinRequest)
    {
        Authentication authentication = null;

        String passwordFromDB;
        Optional<User> temp = userRepository.findUserByUsername(signinRequest.getUsername());
        if(temp.isPresent())
        {
            passwordFromDB = temp.get().getPassword();
            if(encoder.matches(signinRequest.getPassword(), passwordFromDB))
            {
                try
                {
                    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()/*passwordFromDB*/));            
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtCore.generateToken(authentication);
                    if(jwt.isEmpty())
                    {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    return ResponseEntity.ok(jwt);
                }
                catch(BadCredentialsException e)
                {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok("hui tam");
    }
}
