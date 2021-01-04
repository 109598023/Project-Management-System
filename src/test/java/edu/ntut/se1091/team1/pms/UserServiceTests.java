package edu.ntut.se1091.team1.pms;

import edu.ntut.se1091.team1.pms.dto.request.UserRequest;
import edu.ntut.se1091.team1.pms.entity.Role;
import edu.ntut.se1091.team1.pms.entity.User;
import edu.ntut.se1091.team1.pms.repository.RoleRepository;
import edu.ntut.se1091.team1.pms.repository.UserRepository;
import edu.ntut.se1091.team1.pms.service.RoleService;
import edu.ntut.se1091.team1.pms.service.UserService;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Optional;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void userServiceTest() {
        UserRequest userRequest = new UserRequest();

        userRequest.setEmail("testtest3@test.test");
        userRequest.setUsername("testtest3");
        userRequest.setPassword("testtest3");

        Optional<User> userOptional =  userService.save(userRequest);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Assert.assertEquals("testtest3", user.getUsername());
            Optional<Role> roleOptional = roleService.queryAndSave("Role_User");
        }
    }
}
