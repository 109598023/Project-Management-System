package edu.ntut.se1091.team1.pms.controller.project.view;

import edu.ntut.se1091.team1.pms.service.project.view.ContributorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/project_view/github", produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubController {

    @Autowired
    private ContributorsService contributorsService;

    @PostMapping("/contributors")
    public ResponseEntity<String> contributors() {
        return ResponseEntity.ok().body(contributorsService.queryContributors(""));
    }
}
