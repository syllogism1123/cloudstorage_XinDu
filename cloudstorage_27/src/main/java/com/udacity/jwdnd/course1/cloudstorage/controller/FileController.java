package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
@ControllerAdvice
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")

    public String handleFileUpload(@RequestParam(name = "fileUpload") MultipartFile file, Authentication authentication, Model model) {
        User user = this.userService.getUser(authentication.getPrincipal().toString());
        if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            model.addAttribute("errorMessage", "Please select a file to upload");
            return "result";
        }

        if (!this.fileService.isFilenameAvailable(file.getOriginalFilename(), user.getUserId())) {
            model.addAttribute("errorMessage", "File with the same filename already exists");
            return "result";
        }

        try {
            this.fileService.insert(file, user.getUserId());
            List<File> files = this.fileService.getAllFilesByUserId(user.getUserId());
            model.addAttribute("successMessage", "File saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "result";
    }


    @GetMapping("/file-delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Authentication authentication, Model model) {
        User user = this.userService.getUser(authentication.getPrincipal().toString());
        model.addAttribute("successMessage", false);
        model.addAttribute("errorMessage", false);

        try {
            this.fileService.deleteFile(fileId);
            this.fileService.getAllFilesByUserId(user.getUserId());
            model.addAttribute("successMessage", "You've successfully deleted the credential.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "There was an error with deleting the credential... Please try again.");
        }
        return "result";
    }

    @GetMapping("/file-view/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) {
        File file = this.fileService.getFileById(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(new ByteArrayResource(file.getFileData()));
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model) {
        model.addAttribute("successMessage", false);
        model.addAttribute("errorMessage", "The size of the file you wanted to upload has exceeded limit of this software.");

        return "result";
    }

}
