package com.kush.banbah.soloprojectbackend;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("wohoo");
    }

    @GetMapping("/testrole")
    public ResponseEntity<String> testRole() {
        return ResponseEntity.ok("wohoo");
    }
}
