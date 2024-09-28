package com.nav.rentread;

import com.nav.rentread.entities.User;
import org.apache.catalina.connector.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RentReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentReadApplication.class, args);
    }



    @GetMapping
    public ResponseEntity<User> initial(@AuthenticationPrincipal User user){
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
