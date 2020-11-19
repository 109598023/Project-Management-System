package edu.ntut.se1091.team1.pms.web.controller;

import edu.ntut.se1091.team1.pms.persistence.dao.UserRepository;
import edu.ntut.se1091.team1.pms.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
