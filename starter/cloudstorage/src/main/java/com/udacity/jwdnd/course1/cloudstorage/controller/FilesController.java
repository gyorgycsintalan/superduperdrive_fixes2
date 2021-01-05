package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FilesController {

    private FileService fileService;
    private UserService userService;

    public FilesController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    private Integer getCurrtentUserId(Authentication authentication) {
        return userService.getUser(authentication.getName()).getUserid();
    }

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
    }

}
