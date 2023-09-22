package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.controller.file.ResponseAndRequest.FileListResponse;
import com.kush.banbah.soloprojectbackend.database.classes.Class;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;
import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions.FileAlreadyExistsException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions.FileIsEmptyException;
import com.kush.banbah.soloprojectbackend.exceptions.entityDoesNotBelongToClass.UserDoesNotBelongToClassException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.ClassDoesNotExistException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.FileNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class FileService {
    private final Path rootLocation;
    private final ClassRepo classRepo;

    public void store(String className, Authentication auth, MultipartFile file) throws IOException, InvalidRequestException, EntityNotFoundException, EntityDoesNotBelongException {

        verifyUser(className, auth);

        if (file.isEmpty())
            throw new FileIsEmptyException("File is empty!");

        Path destinationFile = this.rootLocation
                .resolve(Paths.get(className, file.getOriginalFilename()))
                .normalize().toAbsolutePath();


        if (Files.exists(destinationFile))
            throw new FileAlreadyExistsException("File " + file.getOriginalFilename() + " already exists");


        InputStream inputStream = file.getInputStream();
        Files.copy(inputStream, destinationFile);
        inputStream.close();


    }


    public List<FileListResponse> getAllFiles(String className, Authentication auth) throws ClassDoesNotExistException, UserDoesNotBelongToClassException, IOException {
        Path classPath = verifyUser(className, auth);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy, hh:mma");

        return Files.walk(classPath, 1)
                .filter(path -> !path.equals(classPath))
                .map(path -> {
                    try {
                        System.out.println(path.getFileName());
                        return FileListResponse.builder()
                                .fileName(path.getFileName().toString())
                                .fileType(Files.probeContentType(path))
                                .fileSize(Files.size(path))
                                .fileUploadDate(formatter.format(new Date(Files.readAttributes(path, BasicFileAttributes.class).creationTime().toMillis())))
                                .build();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();


    }

    private Path verifyUser(String className, Authentication auth) throws ClassDoesNotExistException, UserDoesNotBelongToClassException {
        User loggedUser = (User) auth.getPrincipal();

        Class requestedClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));

        if (loggedUser.getRole() == User.Role.STUDENT) {

            if (!classRepo.findClassByStudents(loggedUser).contains(requestedClass))
                throw new UserDoesNotBelongToClassException("Student " + loggedUser.getName() + " is not in " + className);
        } else if (requestedClass.getTeacher().getId() != loggedUser.getId())
            throw new UserDoesNotBelongToClassException("Teacher " + loggedUser.getName() + " does not teach " + className);

        Path classPath = Path.of(rootLocation.toString(), className);
        return classPath;
    }

    public void delete(String className, Authentication auth, String fileName) throws EntityNotFoundException, EntityDoesNotBelongException, IOException {

        User loggedUser = (User) auth.getPrincipal();

        Class requestedClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));


        if (requestedClass.getTeacher().getId() != loggedUser.getId())
            throw new UserDoesNotBelongToClassException("Teacher " + loggedUser.getName() + " does not teach " + className);


        Path destinationFile = this.rootLocation
                .resolve(Paths.get(className, fileName))
                .normalize().toAbsolutePath();


        if (!Files.exists(destinationFile))
            throw new FileNotExistException("File " + fileName + " does not exist");

        Files.delete(destinationFile);

    }

    public byte[] loadFile(String className, String fileName, Authentication auth) throws EntityDoesNotBelongException, EntityNotFoundException, IOException {

        Path classPath = verifyUser(className, auth);

        classPath = classPath.resolve(fileName);

        if (!Files.exists(classPath)) {
            throw new FileNotExistException("File " + fileName + " in class " + className + " does not exist");
        }
        return Files.readAllBytes(classPath);
    }
}
