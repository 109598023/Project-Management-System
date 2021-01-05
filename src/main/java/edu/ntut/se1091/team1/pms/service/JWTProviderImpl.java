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

    private static final String TOKEN_KEY = "cjZh2yg/xHMoUNiCx2uU+J5oxIkN8NuOICd/4vlj2iQ";

    private static final String TOKEN_TYPE = "tokenType";

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    private static final int ACCESS_TOKEN_EXPIRATION_SEC = 3600;

    private static final int REFRESH_TOKEN_EXPIRATION_SEC = 86400;

    @Override
    public String generateAccessToken(String subject) {
        return generateToken(subject, ACCESS_TOKEN_EXPIRATION_SEC, Map.of(TOKEN_TYPE, ACCESS_TOKEN));
    }

    @Override
    public String generateRefreshToken(String subject) {
        return generateToken(subject, REFRESH_TOKEN_EXPIRATION_SEC, Map.of(TOKEN_TYPE, REFRESH_TOKEN));
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
            return claims.get(TOKEN_TYPE).equals(ACCESS_TOKEN);
        } catch (RuntimeException e) {
        }
        return false;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.get(TOKEN_TYPE).equals(REFRESH_TOKEN);
        } catch (RuntimeException e) {
            return false;
        }
    }

    private Claims parseToken(String token) {
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return parser.parseClaimsJws(token).getBody();
    }

    private String generateToken(String subject, int expirationTimeBySecond, Map<String, Object> data) {
        Claims claims = Jwts.claims();
        claims.setSubject(subject);
        claims.putAll(data);
        Calendar calendar = Calendar.getInstance();
        claims.setIssuedAt(calendar.getTime());
        calendar.add(Calendar.SECOND, expirationTimeBySecond);
        claims.setExpiration(calendar.getTime());
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());

        return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    }
}
