package org.example.cems.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.cems.model.User;
import org.springframework.security.core.parameters.P;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    @Select("SELECT * FROM users WHERE id = #{userId}")
    User findByUserId(@Param("userId") long userId);

    @Insert("INSERT INTO users(email,password,username,role) VALUES (#{email},#{password},#{username},#{role})")
    void insertUser(User user);

    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    Boolean existsByEmail(@Param("email")String email);

}
