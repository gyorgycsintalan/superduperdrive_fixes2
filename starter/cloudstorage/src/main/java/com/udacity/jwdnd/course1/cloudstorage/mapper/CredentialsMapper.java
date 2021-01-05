package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

/*
CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);
 */

@Mapper
public interface CredentialsMapper {

    @Insert("insert into credentials (url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public int addCredential(Credential credential);

    @Update("update credentials " +
            "set url = #{url}, username = #{username}, password = #{password}, key = #{key}" +
            "where credentialid = #{credentialid} and userid = #{userid}")
    public int updateCredential(Credential credential);

    @Select("select * from credentials where userid = #{userid}")
    public Credential[] getCredentials(Integer userid);

    @Delete("delete from credentials where credentialid = #{credentialid} and userid = #{userid}")
    public int deleteCredential(Integer credentialid, Integer userid);

}
