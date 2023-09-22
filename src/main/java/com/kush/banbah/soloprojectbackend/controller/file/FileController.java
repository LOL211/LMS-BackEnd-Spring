package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.controller.file.ResponseAndRequest.FileListResponse;
import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;
import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;
import com.kush.banbah.soloprojectbackend.exceptions.entityDoesNotBelongToClass.UserDoesNotBelongToClassException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.ClassDoesNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/file")
public class FileController {


    private final FileService storageService;

    @PostMapping("/teacher/{className}")
    public ResponseEntity<String> uploadFile(@PathVariable String className, @RequestParam MultipartFile file, Authentication auth) throws InvalidRequestException, EntityDoesNotBelongException, EntityNotFoundException, IOException {

            storageService.store(className, auth, file);
            return ResponseEntity
                    .status(201)
                    .body("File saved!");

    }

    @DeleteMapping ("/teacher/{className}/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String className, @PathVariable String fileName, Authentication auth) throws EntityDoesNotBelongException, EntityNotFoundException, IOException {

        storageService.delete(className, auth, fileName);
        return ResponseEntity
                .status(200)
                .body("File deleted!");

    }

    @GetMapping("/{className}")
    public ResponseEntity<List<FileListResponse>> getListFiles(@PathVariable String className, Authentication auth) throws ClassDoesNotExistException, IOException, UserDoesNotBelongToClassException {
        return ResponseEntity
                .status(200)
                .body(storageService.getAllFiles(className,auth));
    }

    @GetMapping("/{className}/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String className, @PathVariable String fileName, Authentication auth) throws EntityDoesNotBelongException, EntityNotFoundException, IOException {


        return ResponseEntity
                .status(200)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(storageService.loadFile(className,fileName, auth));
    }
}