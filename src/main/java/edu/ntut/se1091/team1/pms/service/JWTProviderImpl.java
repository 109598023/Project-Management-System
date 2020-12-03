package edu.ntut.se1091.team1.pms.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JWTProviderImpl implements JWTProvider {

    private final String tokenKey = "cjZh2yg/xHMoUNiCx2uU+J5oxIkN8NuOICd/4vlj2iQ";

    private final int accessTokenExpirationSec = 3600;

    private final int refreshTokenExpirationSec = 86400;

    @Override
    public String generateAccessToken(String subject) {
        return generateToken(subject, 30, Map.of("tokenType", "ACCESS_TOKEN"));
    }

    @Override
    public String generateRefreshToken(String subject) {
        return generateToken(subject, refreshTokenExpirationSec, Map.of("tokenType", "REFRESH_TOKEN"));
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    @Override
    public boolean validateAccessToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.get("tokenType").equals("ACCESS_TOKEN");
        } catch (RuntimeException e) {
        }
        return false;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.get("tokenType").equals("REFRESH_TOKEN");
        } catch (RuntimeException e) {
        }
        return false;
    }

    private Claims parseToken(String token) {
        Key secretKey = Keys.hmacShaKeyFor(tokenKey.getBytes());
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims;
    }

    private String generateToken(String subject, int expirationTimeBySecond, Map<String, Object> data) {
        Claims claims = Jwts.claims();
        claims.setSubject(subject);
        claims.putAll(data);
        Calendar calendar = Calendar.getInstance();
        claims.setIssuedAt(calendar.getTime());
        calendar.add(Calendar.SECOND, expirationTimeBySecond);
        claims.setExpiration(calendar.getTime());
        Key secretKey = Keys.hmacShaKeyFor(tokenKey.getBytes());

        return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    }
}
