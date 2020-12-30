package edu.ntut.se1091.team1.pms.service;

import edu.ntut.se1091.team1.pms.dto.request.UserRequest;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> save(UserRequest userRequest) {
        List<User> userOptional = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail());
        if (!userOptional.isEmpty()) {
            throw new ConflictException("The username has been used.");
        }
        Optional<Role> roleOptional = roleService.queryAndSave("Role_User");
        System.out.println(roleOptional.get().getName());
        if (roleOptional.isPresent()) {

            User user = new User(userRequest.getEmail(), userRequest.getUsername(),
                    passwordEncoder.encode(userRequest.getPassword()), List.of(roleOptional.get()));

            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> queryUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> queryEmail(String email) {
        return userRepository.findByEmail(email);
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

    @Override
    public List<User> queryAllByUsername(List<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
