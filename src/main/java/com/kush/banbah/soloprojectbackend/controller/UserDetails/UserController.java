package com.kush.banbah.soloprojectbackend.controller.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> retrieveUserDetails()
    {
        System.out.println("got request");
        String result;
        try{
            result = userService.retrieveUserDetails();
        }
        catch(NullPointerException e)
        {
            return ResponseEntity
                    .status(404)
                    .body("Could not find user!");
        }
        catch(ClassCastException | JsonProcessingException  e)
        {
            return ResponseEntity
                    .status(500)
                    .body("Unknown error");
        }

        return ResponseEntity
                .status(200)
                .body(result);
    }
}
