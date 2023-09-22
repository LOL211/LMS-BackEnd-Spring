package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.database.StoredFiles.StoredFile;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions.FileAlreadyExistsException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions.FileIsEmptyException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.FileNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



@Service
@AllArgsConstructor
public class FileService {
    private final Path rootLocation;

    public void store(MultipartFile file) throws IOException, InvalidRequestException {


        if (file.isEmpty())
                throw new FileIsEmptyException("File is empty!");

            Path destinationFile = this.rootLocation
                                    .resolve(Paths.get(file.getOriginalFilename()))
                                    .normalize().toAbsolutePath();


            if(destinationFile.getFileName()!=null)
                throw new FileAlreadyExistsException("File "+file.getOriginalFilename()+" already exists");

            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile);
            inputStream.close();




    }

    public StoredFile getFile(int fileID) throws FileNotExistException {
        return null;
    }

    public List<StoredFile> getAllFiles() {
        return null;
    }
}
