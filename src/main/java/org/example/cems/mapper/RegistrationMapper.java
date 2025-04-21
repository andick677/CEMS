package org.example.cems.mapper;

import org.apache.ibatis.annotations.*;
import org.example.cems.model.Registration;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface RegistrationMapper {

    @Insert("INSERT INTO registrations(user_id,event_id,status) VALUES (#{userId},#{eventId},#{status})")
    void addEvent(@Param("userId")long userId,@Param("eventId")long eventId,@Param("status")String status);

    @Select("SELECT user_id, event_id,status FROM registrations WHERE user_id = #{userId}")
    List<Registration> findByUserId(@Param("userId")long userId);

    @Update("UPDATE registrations SET status = #{status} WHERE user_id = #{userId} AND event_id = #{eventId}")
    void setStatus(@Param("userId")long userId,@Param("eventId")long eventId,@Param("status")String status);

    @Select("SELECT * FROM registrations WHERE status IN ('PENDING', 'REGISTERED')")
    List<Registration> getAllRegistrations();

    @Select("SELECT COUNT(*) FROM registrations WHERE event_id = #{eventId} AND status = 'REGISTERED'")
    int getCurrentPeople(@Param("eventId")long eventId);


}
