package com.example.userserver.mapper;

import com.example.commons.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where name = #{name} and email = #{email}")
    User getByUser(@Param("name") String name, @Param("email") String email);

}
