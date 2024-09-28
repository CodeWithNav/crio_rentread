package com.nav.rentread.contoller;


import com.nav.rentread.dto.UserRegisterDto;
import com.nav.rentread.entities.Rental;
import com.nav.rentread.entities.User;
import com.nav.rentread.service.RentalService;
import com.nav.rentread.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;
    final private RentalService rentalService;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDto userRegisterDto) {
        User registeredUser =userService.register(userRegisterDto);
        registeredUser.setPassword(null);
        return ResponseEntity.ok(registeredUser);
    }


    @GetMapping("/my-books")
    public ResponseEntity<Collection<Rental>> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(rentalService.getUserBooks(user.getId()));
    }


}
