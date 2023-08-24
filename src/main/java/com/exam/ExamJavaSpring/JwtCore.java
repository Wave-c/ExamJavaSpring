package com.exam.ExamJavaSpring;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.exam.ExamJavaSpring.entyties.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtCore {
    @Value("${com.exam.secret}")
    public String secret;
    @Value("${com.exam.lifeTime}")
    public String lifeTime;

    public String generateToken(Authentication authentication)
    {
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        return Jwts.builder().setSubject((userDetails.getUsername())).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + lifeTime))
            .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getNameFromJwt(String jwt)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
