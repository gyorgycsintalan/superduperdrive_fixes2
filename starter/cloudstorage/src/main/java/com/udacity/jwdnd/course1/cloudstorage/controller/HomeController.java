package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class HomeController {

    private FileService fileService;
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    private Integer getCurrtentUserId(Authentication authentication) {
        return userService.getUser(authentication.getName()).getUserid();
    }

    @GetMapping("/home")
    public String homeView(@ModelAttribute("newNote") Note note,
                           @ModelAttribute("newCredential") Credential credential,
                           Model model,
                           Authentication authentication) {
        Integer currentUserId = getCurrtentUserId(authentication);

        model.addAttribute("filenames", fileService.getfilenames(currentUserId));
        model.addAttribute("notes", noteService.getNotes(currentUserId));
        model.addAttribute("credentials", credentialService.getCredentials(currentUserId));

        return "home";
    }
/*
    //simple file storage related methods
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication) {
        Integer currentUserId = getCurrtentUserId(authentication);

        try {
            if (fileService.isFileExists(file.getOriginalFilename(), currentUserId))
                throw (new Exception("File already exists in database!"));
            if (file.getOriginalFilename() == "")
                throw (new Exception("No file selected!"));
            fileService.saveFile(file, currentUserId);
            model.addAttribute("success", true);
        } catch (Exception e) {
            System.out.println( e.getMessage());
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "/result";
    }

    @RequestMapping("/delete/{filename}")
    public String deleteFile(@PathVariable String filename, Model model, Authentication authentication) {
        if (fileService.deleteFile(filename, getCurrtentUserId(authentication)) != 0)
            model.addAttribute("success", true);
        else model.addAttribute("fail", true);

        return "/result";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename, Authentication authentication) {

        File file = fileService.getFile(filename, getCurrtentUserId(authentication));



        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file.getFiledata());
    } */
/*
    //note management related methods
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
*/
    //credential management related methods
/*    @PostMapping("/save-credential")
    public String addCredential(@ModelAttribute("newCredential") Credential credential, Model model, Authentication authentication) {
        Integer currentUserId = getCurrtentUserId(authentication);
        int ret;

        credential.setUserid(currentUserId);

        if (credential.getCredentialid() == null) {
            //new credential is going to be saved
            ret = credentialService.addCredential(credential);
        } else {
            //update existing credential
            ret = credentialService.updateCredential(credential);
        }

        if (ret != 0)
            model.addAttribute("success", true);
        else
            model.addAttribute("fail", true);

        return "/result";
    }

    @RequestMapping("/delete-credential/{credentialid}")
    public String deleteCredential(@PathVariable Integer credentialid, Model model, Authentication authentication) {

        if (credentialService.deleteCredential(credentialid, getCurrtentUserId(authentication)) != 0)
            model.addAttribute("success", true);
        else
            model.addAttribute("fail", true);

        return "result";
    }*/
}
