package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public int addNote(Note note) {
        return notesMapper.addNote(note);
    }


    public int updateNote(Note note) {
        return notesMapper.updateNote(note);
    }

    public int deleteNote(Integer noteid, Integer userid) { return notesMapper.deleteNote(noteid, userid); }

    public Note[] getNotes(Integer userid) {
        return notesMapper.getNotes(userid);
    }

}
