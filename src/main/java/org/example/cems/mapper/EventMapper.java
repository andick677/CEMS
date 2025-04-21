package org.example.cems.mapper;

import org.apache.ibatis.annotations.*;
import org.example.cems.model.Event;
import org.springframework.security.core.parameters.P;

import javax.xml.crypto.Data;
import java.util.List;

@Mapper
public interface EventMapper {

    @Select("Select * FROM events WHERE status = 'OPen'")
    List<Event> getOpenEvents();

    @Select("Select * FROM events")
    List<Event> getAllEvents();

    @Update("UPDATE events SET " +
            "title = #{title}, " +
            "description = #{description}, " +
            "event_date = #{eventDate}, " +
            "max_participants = #{maxParticipants}, " +
            "status = #{status} " +
            "WHERE id = #{id}")
    int ReviseEvent(Event event);

    @Insert("INSERT  INTO events(title,description,event_date,max_participants,status) VALUES (#{title},#{description},#{eventDate},#{maxParticipants},#{status})")
    int createEvent(Event event);

    @Delete("DELETE FROM events WHERE id = #{eventId}")
    int deleteEvent(@Param("eventId")long eventId);

    @Select("Select * FROM events WHERE id = #{eventId}")
    Event findByEventId(@Param("eventId")long eventId);



}
