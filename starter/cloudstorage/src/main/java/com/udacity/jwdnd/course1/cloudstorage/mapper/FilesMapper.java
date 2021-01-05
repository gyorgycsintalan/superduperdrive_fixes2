package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

/*CREATE TABLE IF NOT EXISTS FILES (
        fileId INT PRIMARY KEY auto_increment,
        filename VARCHAR,
        contenttype VARCHAR,
        filesize VARCHAR,
        userid INT,
        filedata BLOB,
        foreign key (userid) references USERS(userid)
        );
*/

@Mapper
public interface FilesMapper {

    @Insert("insert into files (filename, contenttype, filesize, userid, filedata)" +
            "values(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int insertFile(File file);

    @Select("select * from files where filename = #{filename} and userid = #{userid}")
    public File getFile(String filename, Integer userid);

    @Delete("delete from files where filename = #{filename} and userid = #{userid}")
    public int deleteFile(String filename, Integer userid);

    @Select("select filename from files where userid = #{userid}")
    public String[] getFilenames(Integer userid);

    @Select("select count(*) from files where filename = #{filename} and userid = #{userid}")
    public int isFileExists(String filename, Integer userid);
}
