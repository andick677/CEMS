package org.example.cems.service;

import org.example.cems.mapper.EventMapper;
import org.example.cems.mapper.RegistrationMapper;
import org.example.cems.model.Event;
import org.example.cems.model.Registration;
import org.example.cems.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    RegistrationMapper registrationMapper;

    @Autowired
    EventMapper eventMapper;

    public String addEvent(long userId,long eventId){
        //判斷活動人數是否已滿
        int currentPeople  = registrationMapper.getCurrentPeople(eventId);
        List<Event> Openlist = eventMapper.getOpenEvents();
        Event matchedEvent=null;

        for(Event event : Openlist){
            if(event.getId() == eventId){
                matchedEvent = event;
                break;
            }
        }

        if(currentPeople >= matchedEvent.getMaxParticipants()){
            return "活動已滿人無法再報名";
        }

        //判斷是否申請過
        List<Registration> list = registrationMapper.findByUserId(userId);

        if (list.isEmpty()){
            registrationMapper.addEvent(userId,eventId,"PENDING");
            return "活動請求已發送";
        }

        for(Registration registration : list){
            if(registration.getEventId() == eventId && registration.getStatus().equals("CANCELLED")){
                registrationMapper.setStatus(userId,eventId,"PENDING");
                return "已重新申請此活動";
            }

            if(registration.getEventId() == eventId ){
                return "你已經申請過此活動";
            }
        }

        registrationMapper.addEvent(userId,eventId,"PENDING");
        return "活動請求已發送";

    }

    public List<Registration> trackAddEvent(long userId){
        return registrationMapper.trackAddEvent(userId);
    }

    public String cancelEvent(long userId,long eventId){
        List<Registration> list = registrationMapper.findByUserId(userId);

        for(Registration registration : list){
            if(registration.getEventId() == eventId){
                registrationMapper.setStatus(userId,eventId,"CANCELLED");
                return "成功取消報名";
            }
        }

        return "您沒有報名過這個活動，所以不需要取消";
    }

    public List<Registration> getAllRegistrations(){
        return registrationMapper.getAllRegistrations();
    }

    public String checkRegistration(long userId,long eventId){
        int currentPeople  = registrationMapper.getCurrentPeople(eventId);
        List<Event> list = eventMapper.getOpenEvents();
        Event matchedEvent=null;
        for(Event event : list){
            if(event.getId() == eventId){
                matchedEvent = event;
                break;
            }
        }

        if(currentPeople > matchedEvent.getMaxParticipants()){
            return "活動人數已達上限";
        }

        registrationMapper.setStatus(userId,eventId,"REGISTERED");
        return "同意此同學參加活動";
    }

    public int getCurrentPeople(long eventId){
        return registrationMapper.getCurrentPeople(eventId);
    }

    public List<Registration> getRegistrationUserId(int eventId){
        return registrationMapper.getRegistrationUserId(eventId);
    }

}
