package edu.ntut.se1091.team1.pms.controller;

import edu.ntut.se1091.team1.pms.auth.AuthRequest;
import edu.ntut.se1091.team1.pms.auth.JWTService;
import edu.ntut.se1091.team1.pms.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private JWTService jwtService;

    @PostMapping
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody AuthRequest request) {
        String token = jwtService.generateToken(request);
        Map<String, String> response = Collections.singletonMap("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/parse")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Optional<Map<String, Object>> response = jwtService.parseToken(token);
        if (response.isEmpty()) {
            throw new UnauthorizedException();
        }
        return ResponseEntity.ok(response.get());
    }
}