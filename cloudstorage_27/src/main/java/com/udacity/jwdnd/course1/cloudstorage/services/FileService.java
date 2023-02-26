package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public void insert(MultipartFile file, Integer userId) throws IOException {
        this.fileMapper.insert(new File(file.getOriginalFilename(), file.getContentType(),
                file.getSize() + "", userId, file.getBytes()));
    }

    public boolean isFilenameAvailable(String filename, Integer userId) {
        return this.fileMapper.getFile(filename, userId) == null;

    }

    public List<File> getAllFilesByUserId(Integer userId) {
        return this.fileMapper.getFilesByUserId(userId);
    }

    public void deleteFile(Integer fileId) {
        this.fileMapper.deleteFile(fileId);
    }

    public File getFileById(Integer fileId) {
        return this.fileMapper.getFileById(fileId);
    }
}
