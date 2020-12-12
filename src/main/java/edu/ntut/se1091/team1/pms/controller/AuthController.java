package edu.ntut.se1091.team1.pms.controller;

import edu.ntut.se1091.team1.pms.dto.RefreshTokenRequest;
import edu.ntut.se1091.team1.pms.exception.UnauthorizedException;
import edu.ntut.se1091.team1.pms.service.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (StringUtils.hasText(refreshToken) && jwtProvider.validateRefreshToken(refreshToken)) {
            String username = jwtProvider.getUsernameFromToken(refreshToken);
            String accessToken = jwtProvider.generateAccessToken(username);
            refreshToken = jwtProvider.generateRefreshToken(username);
            return ResponseEntity.ok().body(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        } else {
            throw new UnauthorizedException();
        }
    }
}