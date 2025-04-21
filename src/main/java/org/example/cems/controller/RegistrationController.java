package org.example.cems.controller;

import org.example.cems.model.Event;
import org.example.cems.model.Registration;
import org.example.cems.resporse.RegisterResponse;
import org.example.cems.service.RegistrationService;
import org.example.request.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @GetMapping("/addevents/{userId}/{eventId}")
    public ResponseEntity<?> addEvent(@PathVariable long userId,@PathVariable long eventId){
        try {
            String message =registrationService.addEvent(userId,eventId);
            return ResponseEntity.ok(new RegisterResponse(message));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @GetMapping("/cancle/{userId}/{eventId}")
    public ResponseEntity<?> cancelEvent(@PathVariable long userId,@PathVariable long eventId){
        try{
            String message =registrationService.cancelEvent(userId,eventId);
            return ResponseEntity.ok(new RegisterResponse(message));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllRegistrations(){
        try{
            List<Registration> list = registrationService.getAllRegistrations();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error"+e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/checkRegistration/{userId}/{eventId}")
    public ResponseEntity<?> checkRegistration(@PathVariable long userId,@PathVariable long eventId){
        try{
            String message = registrationService.checkRegistration(userId,eventId);
            return ResponseEntity.ok(new RegisterResponse(message));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }

    }

    @GetMapping("/getCurrentPeople/{eventId}")
    public ResponseEntity<?> getCurrentPeople(@PathVariable long eventId){
        try {
            int CurrentPeople = registrationService.getCurrentPeople(eventId);
            return ResponseEntity.ok(CurrentPeople);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error"+e.getMessage());
        }
    }

    @GetMapping("/trackAddEvent/{userId}")
    public ResponseEntity<?> trackAddEvent(@PathVariable long userId){
        try {
            List<Registration> list = registrationService.trackAddEvent(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error"+e.getMessage());
        }
    }



}
