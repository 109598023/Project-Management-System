package edu.ntut.se1091.team1.pms.service;


import edu.ntut.se1091.team1.pms.dto.request.UserRequest;
import edu.ntut.se1091.team1.pms.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> save(UserRequest userRequest);

    Optional<User> queryUsername(String username);

    Optional<User> queryEmail(String email);

    List<User> queryAllByUsername(List<String> usernames);
}