package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {
    /*
    CREATE TABLE IF NOT EXISTS NOTES (
        noteid INT PRIMARY KEY auto_increment,
        notetitle VARCHAR(20),
        notedescription VARCHAR (1000),
        userid INT,
        foreign key (userid) references USERS(userid)
    );
     */

    @Insert("insert into notes (notetitle, notedescription, userid) values (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    public int addNote(Note note);

    @Update("update notes " +
            "set notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "where noteid = #{noteid} and userid = #{userid}")
    public int updateNote(Note note);

    @Select("select * from notes where userid = #{userid}")
    public Note[] getNotes(Integer userid);

    @Select("select * from notes where notetile = #{notetitle} and userid = #{userid}")
    public Note getNote(String notetitle, Integer userid);

    @Delete("delete from notes where noteid = #{noteid} and userid = #{userid}")
    public int deleteNote(Integer noteid, Integer userid);
}
