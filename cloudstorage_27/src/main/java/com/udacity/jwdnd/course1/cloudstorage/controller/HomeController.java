package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homeView(Authentication auth, Model model,
                           @ModelAttribute("newCredential") Credential credential,
                           @ModelAttribute("newNote") Note note) {
        User user = userService.getUser(auth.getPrincipal().toString());
        Integer userId = user.getUserId();
        model.addAttribute("files", this.fileService.getAllFilesByUserId(userId));
        model.addAttribute("notes", this.noteService.getNotesByUser(userId));
        model.addAttribute("credentials", this.credentialService.getAllCredentialsByUser(userId));
        model.addAttribute("credentialService", this.credentialService);

        return "home";
    }

}
