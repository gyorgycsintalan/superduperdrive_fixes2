package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/*CREATE TABLE IF NOT EXISTS USERS (
        userid INT PRIMARY KEY auto_increment,
        username VARCHAR(20),
        salt VARCHAR,
        password VARCHAR,
        firstname VARCHAR(20),
        lastname VARCHAR(20)
        );
*/

@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username}")
    public User getUser(String username);

    @Insert("insert into users (username, salt, password, firstname, lastname) " +
            "values(#{username} ,#{salt} ,#{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    public int addUser(User user);
}
