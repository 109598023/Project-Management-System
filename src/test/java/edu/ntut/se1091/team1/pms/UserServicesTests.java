package edu.ntut.se1091.team1.pms;
import edu.ntut.se1091.team1.pms.dto.request.UserRequest;
import edu.ntut.se1091.team1.pms.entity.User;
import edu.ntut.se1091.team1.pms.exception.ConflictException;
import edu.ntut.se1091.team1.pms.service.UserService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServicesTests {

    @BeforeAll
    static void setup(){
    }

    @Autowired
    UserService userService;

    @Test
    @Order(2)
    void userServiceSaveCorrectRequestTest(){
        Optional<User> user;
        String email = "test1@test.test";
        String username = "test1";
        String password = "test1";
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email);
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        user = userService.save(userRequest);
        if(user.isPresent()){
            User us = user.get();
            Assertions.assertEquals(email,us.getEmail());
            Assertions.assertEquals(username,us.getUsername());
        }else{
            Assertions.fail();
        }
    }

    @Test
    @Order(5)
    void  userServiceSaveSameUsernameTest(){
        String email = "test2@test.test";
        String username = "test2";
        String password = "test2";
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email);
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userService.save(userRequest);
        userRequest.setEmail("test3@test.test");
        try{
            userService.save(userRequest);
            Assertions.fail();
        }catch (ConflictException e){
            Assertions.assertEquals("The username has been used.",e.getMessage());
        }
    }

    @Test
    @Order(4)
    void  userServiceSaveSameEmailTest(){
        String email = "test3@test.test";
        String username = "test3";
        String password = "test3";
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email);
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userService.save(userRequest);
        userRequest.setUsername("test4");
        try{
            userService.save(userRequest);
            Assertions.fail();
        }catch (ConflictException e){
            Assertions.assertEquals("The username has been used.",e.getMessage());
        }
    }

    @Test
    @Order(3)
    void  userServiceFindUsername (){
        Optional<User> user;
        String email = "test4@test.test";
        String username = "test4";
        String password = "test4";
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email);
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userService.save(userRequest);
        if((user = userService.queryUsername(username)).isPresent()){
            User us = user.get();
            Assertions.assertEquals(email,us.getEmail());
            Assertions.assertEquals(username,us.getUsername());
        }else{
            Assertions.fail();
        }
    }

    @Test
    @Order(1)
    void  userServiceFindEmail (){
        Optional<User> user;
        String email = "test5@test.test";
        String username = "test5";
        String password = "test5";
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email);
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userService.save(userRequest);
        if((user = userService.queryEmail(email)).isPresent()){
            User us = user.get();
            Assertions.assertEquals(email,us.getEmail());
            Assertions.assertEquals(username,us.getUsername());
        }else{
            Assertions.fail();
        }
    }

    @Test
    @Order(6)
    void userServiceFindAllByUsername (){
        List<String> usernames = new ArrayList<>();
        List<User>   queryResult;
        usernames.add("test1");
        usernames.add("test3");
        usernames.add("test5");
        usernames.add("test8");
        usernames.add("test2@test.test");
        queryResult = userService.queryAllByUsername(usernames);
        Assertions.assertEquals(3,queryResult.size());
        Assertions.assertEquals("test5@test.test",queryResult.get(0).getEmail());
        Assertions.assertEquals("test5",queryResult.get(0).getUsername());
        Assertions.assertEquals("test1@test.test",queryResult.get(1).getEmail());
        Assertions.assertEquals("test1",queryResult.get(1).getUsername());
        Assertions.assertEquals("test3@test.test",queryResult.get(2).getEmail());
        Assertions.assertEquals("test3",queryResult.get(2).getUsername());
    }

}
