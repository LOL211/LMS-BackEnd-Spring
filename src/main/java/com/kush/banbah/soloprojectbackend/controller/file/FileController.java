package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.controller.file.ResponseAndRequest.FileListResponse;
import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;
import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;
import com.kush.banbah.soloprojectbackend.exceptions.entityDoesNotBelongToClass.UserDoesNotBelongToClassException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.ClassDoesNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @DeleteMapping("/teacher/{className}/{fileName}")
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
                .body(storageService.getAllFiles(className, auth));
    }

    @GetMapping("/{className}/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String className, @PathVariable String fileName, Authentication auth) throws EntityDoesNotBelongException, EntityNotFoundException, IOException {

        byte[] bytes = storageService.loadFile(className, fileName, auth);
        return ResponseEntity
                .status(200)
                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.valueOf(URLConnection.guessContentTypeFromName(fileName))))
                .body(bytes);
    }


}