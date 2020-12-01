package edu.ntut.se1091.team1.pms.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JWTService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final String KEY = "cjZh2yg/xHMoUNiCx2uU+J5oxIkN8NuOICd/4vlj2iQ";

    public String generateToken(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        Calendar calendar = Calendar.getInstance();
        claims.setIssuedAt(calendar.getTime());
        calendar.add(Calendar.SECOND, 5);
        claims.setExpiration(calendar.getTime());

        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    }

    public Optional<Map<String, Object>> parseToken(String token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

            JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            Claims claims = parser.parseClaimsJws(token).getBody();
            return  Optional.of(claims.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}