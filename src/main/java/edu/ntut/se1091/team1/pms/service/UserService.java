package edu.ntut.se1091.team1.pms.service;


import edu.ntut.se1091.team1.pms.persistence.model.User;
import edu.ntut.se1091.team1.pms.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);
}