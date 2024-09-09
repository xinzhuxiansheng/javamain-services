package com.javamain.mybatis.dao;

import com.javamain.mybatis.pojo.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM User WHERE id = #{id}")
    User getUserById(int id);
}
