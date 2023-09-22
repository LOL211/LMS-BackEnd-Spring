package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.controller.file.ResponseAndRequest.FileListResponse;
import com.kush.banbah.soloprojectbackend.database.StoredFiles.StoredFile;
import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;
import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions.FileIsEmptyException;
import com.kush.banbah.soloprojectbackend.exceptions.entityDoesNotBelongToClass.UserDoesNotBelongToClassException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.ClassDoesNotExistException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.FileNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

    @GetMapping("/{className}")
    public ResponseEntity<String> getListFiles(@PathVariable String className, Authentication auth) throws ClassDoesNotExistException, IOException, UserDoesNotBelongToClassException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy, hh:mma");
        storageService.getAllFiles(className,auth);
//        List<FileListResponse> files = storageService.getAllFiles(className, auth).stream().map(file ->
//                FileListResponse.builder()
//                        .fileName(file.getFileName())
//                        .fileType(file.getType())
//                        .fileUploadDate(formatter.format(file.getUploadDate()))
//                        .build()).toList();

        return ResponseEntity
                .status(200)
                .body("good scence");
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<String> getFile(@PathVariable String id) throws FileNotExistException {
//        StoredFile file = storageService.getFile(Integer.parseInt(id));

        return ResponseEntity
                .status(200)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body("test");
    }
}