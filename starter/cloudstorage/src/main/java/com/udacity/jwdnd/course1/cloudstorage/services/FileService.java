package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    public static final int MAX_FILESIZE = 1048576; //max size in bytes (1 MB)
    private FilesMapper filesMapper;

    public FileService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public void saveFile(MultipartFile multipartFile, Integer currentUserId) throws IOException {
        filesMapper.insertFile(new File(null,
                                        multipartFile.getOriginalFilename(),
                                        multipartFile.getContentType(),
                                        String.valueOf(multipartFile.getSize()),
                                        currentUserId,
                                        multipartFile.getBytes()));

    }

    public int deleteFile(String filename, Integer userid) {
       return filesMapper.deleteFile(filename, userid);
    }

    public String[] getfilenames(Integer userid) {
        return filesMapper.getFilenames(userid);
    }

    public File getFile(String filename, Integer userid) {
        return filesMapper.getFile(filename, userid);
    }

    public boolean isFileExists(String filename, Integer userid) {
        if (filesMapper.isFileExists(filename, userid) == 0) return false;
        return true;
    }
}
