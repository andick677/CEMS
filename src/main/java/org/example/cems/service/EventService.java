package org.example.cems.service;


import org.example.cems.mapper.EventMapper;
import org.example.cems.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventMapper eventMapper;

    public List<Event> getOpenEvents(){
        return eventMapper.getOpenEvents();
    }

    public String ReviseEvent(Event event){
        eventMapper.ReviseEvent(event);
        return "修改成功";
    }

    public String createEvent(Event event){
        if(eventMapper.createEvent(event)!=0){
            return "新增成功";
        }
        return "新增失敗";
    }

    public String deleteEvent(long eventId){
        eventMapper.deleteEvent(eventId);
        return "刪除成功";
    }

    public List<Event> getAllEvents(){
        return eventMapper.getAllEvents();

    }

    public Event findByEventId(long eventId){
        return eventMapper.findByEventId(eventId);
    }


}
