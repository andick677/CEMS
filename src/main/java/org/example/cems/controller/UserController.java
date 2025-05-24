package org.example.cems.controller;

import org.example.cems.mapper.UserMapper;
import org.example.cems.model.Event;
import org.example.cems.model.User;
import org.example.cems.resporse.RegisterResponse;
import org.example.cems.resporse.UserResporse;
import org.example.cems.service.UserService;
import org.example.cems.util.JwtUtil;
import org.example.request.RegisterRequest;
import org.example.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserRequest userRequest ) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(),userRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new UserResporse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        try{
            String message = userService.registerUser(registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getUsername(),
                    "STUDENT");
            return ResponseEntity.ok(new RegisterResponse(message));
        }catch (RuntimeException e){
            return  ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable long userId){
        try {
            User user = userService.findByUserId(userId);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }










}
