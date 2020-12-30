package edu.ntut.se1091.team1.pms.service;

import edu.ntut.se1091.team1.pms.dto.request.UserRequest;
import edu.ntut.se1091.team1.pms.exception.ConflictException;
import edu.ntut.se1091.team1.pms.repository.UserRepository;
import edu.ntut.se1091.team1.pms.entity.Role;
import edu.ntut.se1091.team1.pms.entity.User;
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

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> save(UserRequest userRequest) {
        if(userRequest.getUsername().isEmpty()||userRequest.getPassword().isEmpty()||userRequest.getEmail().isEmpty()){
            throw new ConflictException("The username or email or password is empty.");
        }
        List<User> userOptional = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail());
        if (!userOptional.isEmpty()) {
            throw new ConflictException("The username or Email has been used.");
        }
        Optional<Role> roleOptional = roleService.queryAndSave("Role_User");
        if (roleOptional.isPresent()) {
            //System.out.println(roleOptional.get().getName());
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
