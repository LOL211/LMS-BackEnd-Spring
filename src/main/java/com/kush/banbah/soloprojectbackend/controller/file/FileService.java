package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.database.StoredFiles.StoredFile;
import com.kush.banbah.soloprojectbackend.database.StoredFiles.StoredFileRepo;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.FileNotExistException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@Service
public class FileService {
    private final StoredFileRepo storedFileRepo;

    public void store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        StoredFile fileEntity = StoredFile.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();

    }

    public StoredFile getFile(int fileID) throws FileNotExistException {
        return storedFileRepo.findById(fileID).orElseThrow(()->new FileNotExistException("File with ID of "+fileID+"does not exist!"));
    }

    public List<StoredFile> getAllFiles() {
        return storedFileRepo.findAll();
    }
}
