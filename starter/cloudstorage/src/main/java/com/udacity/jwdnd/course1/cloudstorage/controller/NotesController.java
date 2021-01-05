package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotesController {

    private NoteService noteService;
    private UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    private Integer getCurrtentUserId(Authentication authentication) {
        return userService.getUser(authentication.getName()).getUserid();
    }

    @PostMapping("/save-note")
    public String addNote(@ModelAttribute("newNote") Note note, Model model, Authentication authentication) {
        Integer currentUserId = getCurrtentUserId(authentication);
        int ret;

        //new note is going to be saved
        note.setUserid(currentUserId);
        if (note.getNoteid() == null) {
            ret = noteService.addNote(note);
        } else {
            //update existing note
            ret = noteService.updateNote(note);
        }

        if (ret == 0)
            model.addAttribute("fail", true);
        else
            model.addAttribute("success", true);

        return "/result";
    }

    @RequestMapping("/delete-note/{noteid}")
    public String deleteFile(@PathVariable Integer noteid, Authentication authentication, Model model) {
        if (noteService.deleteNote(noteid, getCurrtentUserId(authentication)) != 0)
            model.addAttribute("success", true);
        else
            model.addAttribute("fail", true);

        return "/result";
    }

}
