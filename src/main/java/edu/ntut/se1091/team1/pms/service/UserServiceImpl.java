package edu.ntut.se1091.team1.pms.service;

import edu.ntut.se1091.team1.pms.dto.UserRequest;
import edu.ntut.se1091.team1.pms.exception.ConflictException;
import edu.ntut.se1091.team1.pms.repository.UserRepository;
import edu.ntut.se1091.team1.pms.entity.Role;
import edu.ntut.se1091.team1.pms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRequest userRequest) throws ConflictException {
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new ConflictException("The username has been used.");
        }
        User user = new User(userRequest.getEmail(), userRequest.getUsername(),
                passwordEncoder.encode(userRequest.getPassword()), Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User user = existingUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}