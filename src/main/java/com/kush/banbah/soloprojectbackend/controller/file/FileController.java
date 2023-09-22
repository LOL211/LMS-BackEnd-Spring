package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.controller.file.ResponseAndRequest.FileListResponse;
import com.kush.banbah.soloprojectbackend.database.StoredFiles.StoredFile;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.FileNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1/file")
public class FileController {


    private final FileService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {


        try {
            storageService.store(file);
            return ResponseEntity
                    .status(201)
                    .body("File saved!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body("Unable to save file because of IO exception");
        }

    }

    @GetMapping()
    public ResponseEntity<List<FileListResponse>> getListFiles() {
        List<FileListResponse> files = storageService.getAllFiles().stream().map(file ->
                FileListResponse.builder()
                        .fileName(file.getName())
                        .fileType(file.getType())
                        .fileUploadDate(new Date())
                        .fileSize(file.getData().length)
                        .build()).toList();
        return ResponseEntity
                .status(200)
                .body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) throws FileNotExistException {
        StoredFile file = storageService.getFile(Integer.parseInt(id));

        return ResponseEntity
                .status(200)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}