package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CredentialsController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialsController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    private Integer getCurrtentUserId(Authentication authentication) {
        return userService.getUser(authentication.getName()).getUserid();
    }

    @PostMapping("/save-credential")
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
    }

}
