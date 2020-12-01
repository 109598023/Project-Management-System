package edu.ntut.se1091.team1.pms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/test/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ok");
    }
}
