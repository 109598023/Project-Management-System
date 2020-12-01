package edu.ntut.se1091.team1.pms.service;


import edu.ntut.se1091.team1.pms.entity.User;
import edu.ntut.se1091.team1.pms.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);
}