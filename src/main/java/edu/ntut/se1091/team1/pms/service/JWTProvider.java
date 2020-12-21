package edu.ntut.se1091.team1.pms.service;

public interface JWTProvider {

    String generateAccessToken(String subject);

    String generateRefreshToken(String subject);

    String getUsernameFromToken(String token);

    boolean validateAccessToken(String token);

    boolean validateRefreshToken(String token);
}
