package edu.ntut.se1091.team1.pms.controller;

import edu.ntut.se1091.team1.pms.dto.UserRequest;
import edu.ntut.se1091.team1.pms.entity.User;
import edu.ntut.se1091.team1.pms.exception.UnauthorizedException;
import edu.ntut.se1091.team1.pms.service.JWTProvider;
import edu.ntut.se1091.team1.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUserAccount(@RequestBody UserRequest userRequest) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());
            authentication = authenticationManager.authenticate(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtProvider.generateAccessToken(userRequest.getUsername());
            String refreshToken = jwtProvider.generateRefreshToken(userRequest.getUsername());
            return ResponseEntity.ok().body(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUserAccount(@RequestBody UserRequest userRequest) {
        userService.save(userRequest);
        return ResponseEntity.ok().build();
    }

}
