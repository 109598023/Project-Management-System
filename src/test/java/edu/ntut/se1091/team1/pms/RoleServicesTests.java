package edu.ntut.se1091.team1.pms;

import edu.ntut.se1091.team1.pms.service.RoleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleServicesTests
{
    @Autowired
    RoleService roleService;

    String roleName1,roleName2,roleName3;

    @BeforeAll
    void setup(){
        roleName1 = "Owner";
        roleName2 = "User";
        roleName3 = "not-login";
    }

    @Test
    void roleServiceSaveTest(){
        roleService
    }

    @Test
    void roleServiceQueryTest(){

    }

    @Test
    void roleServiceSaveAndQueryTest(){

    }
}
