package org.example.cems.controller;

import org.example.cems.mapper.EventMapper;
import org.example.cems.model.Event;
import org.example.cems.resporse.RegisterResponse;
import org.example.cems.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/open")
    public ResponseEntity<?> getOpenEvents(){
        try {
            List<Event> events = eventService.getOpenEvents();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error"+e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ReviseEvent")
    public ResponseEntity<?> ReviseEvent(@RequestBody Event event){
        try{
            String message = eventService.ReviseEvent(event);
            return ResponseEntity.ok(new RegisterResponse(message));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createEvent")
    public ResponseEntity<?> createEvent(@RequestBody Event event){
        try {
            String message = eventService.createEvent(event);
            return ResponseEntity.ok(new RegisterResponse(message));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable long eventId){
        try {
            String message = eventService.deleteEvent(eventId);
            return ResponseEntity.ok(new RegisterResponse(message));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> findByEventId(@PathVariable long eventId){
        try {
            Event event = eventService.findByEventId(eventId);
            return ResponseEntity.ok(event);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllEvents(){
        try {
            List<Event> events = eventService.getAllEvents();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error"+e.getMessage());
        }
    }



}
