package edu.ntut.se1091.team1.pms.controller;

import edu.ntut.se1091.team1.pms.dto.Response;
import edu.ntut.se1091.team1.pms.entity.User;
import edu.ntut.se1091.team1.pms.service.UserService;
import edu.ntut.se1091.team1.pms.dto.UserRegistrationDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/auth")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUserAccount(@RequestBody UserRegistrationDto registrationDto) {
        User user = userService.save(registrationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();
        return ResponseEntity.created(location).body(user);
    }
}
