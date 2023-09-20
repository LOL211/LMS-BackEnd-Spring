package com.kush.banbah.soloprojectbackend.controller.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kush.banbah.soloprojectbackend.database.classes.Class;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class UserService {
    
    private ClassRepo classRepo;
    public String retrieveUserDetails(Authentication auth) throws NullPointerException, ClassCastException, JsonProcessingException {
       
        ObjectMapper mapper = new ObjectMapper();
        User loggedUser = (User) auth.getPrincipal();
        
        List<Class> classes;
        
        if(loggedUser.getRole()== User.Role.STUDENT)
         classes= classRepo.findClassByStudents(loggedUser);
        else
            classes= classRepo.findClassByTeacher(loggedUser);


        String[] classNames = classes.stream().map(Class::getClassName).toArray(String[]::new);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("classes", classNames);
        objectMap.put("name", loggedUser.getName());
        objectMap.put("role", loggedUser.getRole());


        return mapper.writeValueAsString(objectMap);




    }
}
