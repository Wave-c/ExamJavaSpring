package com.exam.ExamJavaSpring.configurators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.exam.ExamJavaSpring.TokenFilter;
import com.exam.ExamJavaSpring.repositories.UserRoomRepository;
import com.exam.ExamJavaSpring.services.RoomService;
import com.exam.ExamJavaSpring.services.UserRoomService;
import com.exam.ExamJavaSpring.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurator 
{
    private UserService userService;
    private TokenFilter tokenFilterr;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setTokenFilter(TokenFilter tokenFilter)
    {
        this.tokenFilterr = tokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    {
        try
        {
            return authenticationConfiguration.getAuthenticationManager();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Primary
    public AuthenticationManagerBuilder configureAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder)
    {
        try
        {
            authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
            return authenticationManagerBuilder;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
    {
        CorsConfiguration ccs = new CorsConfiguration().applyPermitDefaultValues();
        //ccs.addAllowedMethod("GET, POST");
        //ccs.addAllowedOrigin("http://localhost:3000");
        try
        {
            http
            .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                    .configurationSource(request ->
                    (new CorsConfiguration()).applyPermitDefaultValues()))
                .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/secured/**").fullyAuthenticated()
                    .requestMatchers("/main/**").permitAll()
                    .anyRequest().permitAll())
                .addFilterBefore(tokenFilterr, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}