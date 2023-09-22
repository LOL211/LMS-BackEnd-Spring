package com.kush.banbah.soloprojectbackend.controller.file;


import com.kush.banbah.soloprojectbackend.database.StoredFiles.StoredFile;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.database.classes.Class;
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
import java.util.List;



@Service
@AllArgsConstructor
public class FileService {
    private final Path rootLocation;
    private final ClassRepo classRepo;

    public void store(String className, Authentication auth, MultipartFile file) throws IOException, InvalidRequestException, EntityNotFoundException, EntityDoesNotBelongException {

        User loggedUser = (User) auth.getPrincipal();

        Class requestedClass = classRepo.findByClassName(className).orElseThrow(()->new ClassDoesNotExistException(className));



        if(requestedClass.getTeacher().getId()!=loggedUser.getId())
            throw new UserDoesNotBelongToClassException("Teacher "+loggedUser.getName()+" does not teach "+className);



        if (file.isEmpty())
                throw new FileIsEmptyException("File is empty!");

            Path destinationFile = this.rootLocation
                                    .resolve(Paths.get(className, file.getOriginalFilename()))
                                    .normalize().toAbsolutePath();


            if(Files.exists(destinationFile))
                throw new FileAlreadyExistsException("File "+file.getOriginalFilename()+" already exists");


            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile);
            inputStream.close();




    }

    public StoredFile getFile(int fileID) throws FileNotExistException {
        return null;
    }

    public List<StoredFile> getAllFiles(String className, Authentication auth) throws ClassDoesNotExistException, UserDoesNotBelongToClassException, IOException {
        User loggedUser = (User) auth.getPrincipal();

        Class requestedClass = classRepo.findByClassName(className).orElseThrow(()->new ClassDoesNotExistException(className));

        if(loggedUser.getRole()== User.Role.STUDENT)
        {

            if(classRepo.findClassByStudents(loggedUser).contains(requestedClass))
                throw new UserDoesNotBelongToClassException("Student "+loggedUser.getName()+" is not in "+className);
        }
        else if(requestedClass.getTeacher().getId()!=loggedUser.getId())
                throw new UserDoesNotBelongToClassException("Teacher "+loggedUser.getName()+" does not teach "+className);

        Path classPath = Path.of(rootLocation.toString(),className);

        Files.walk(classPath, 1)
                    .filter(path -> !path.equals(classPath))
                    .map(classPath::relativize)
                    .map(Path::toString)
                    .forEach(System.out::println);


        return null;

    }
}
