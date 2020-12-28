package edu.ntut.se1091.team1.pms.controller.project.view;

import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.service.project.view.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/project_view/github", produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubController {

    @Autowired
    private GithubService githubService;

    @PostMapping("/contributors")
    public ResponseEntity<String> contributors(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(githubService.queryContributors(queryProjectRequest));
    }

    @PostMapping("/contributors_total")
    public ResponseEntity<String> contributorsTotal(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(githubService.queryContributorsTotal(queryProjectRequest));
    }

    @PostMapping("/punch_card")
    public ResponseEntity<String> punchCard(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(githubService.queryPunchCard(queryProjectRequest));
    }
    @PostMapping("/Issue")
    public ResponseEntity<String> Issue(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(githubService.queryIssue(queryProjectRequest));
    }
}
