package com.kush.banbah.soloprojectbackend.controller.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepo userRepo;

    public String retrieveUserDetails() throws NullPointerException, ClassCastException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        UserEntity loggedUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedUser==null) throw new NullPointerException();
        List<String> classNames = userRepo.findClassNamesByUser_id(loggedUser.getId()).orElseThrow(NullPointerException::new);
        String[] test = classNames.toArray(new String[0]);
        classNames.forEach(System.out::println);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("classes", test);
        objectMap.put("name", loggedUser.getName());
//        ObjectNode classesNode = mapper.valueToTree(objectMap);
        return mapper.writeValueAsString(objectMap);



    }
}
