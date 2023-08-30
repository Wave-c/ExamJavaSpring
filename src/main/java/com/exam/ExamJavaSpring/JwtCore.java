package com.exam.ExamJavaSpring;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.exam.ExamJavaSpring.entyties.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtCore {
    public SecretKey secret = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Value("${com.exam.lifeTime}")
    public String lifeTime;

    public String generateToken(Authentication authentication)
    {
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setNotBefore(new Date())
            .setExpiration(Date.from(LocalDateTime.now().plusSeconds(Integer.parseInt(lifeTime))
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getNameFromJwt(String jwt)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
