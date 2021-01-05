package edu.ntut.se1091.team1.pms.controller.project.view;

import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.service.project.view.SonarQubeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/project_view/sonar_qube", produces = MediaType.APPLICATION_JSON_VALUE)
public class SonarQubeController {

    private SonarQubeService sonarQubeService;

    public SonarQubeController(SonarQubeService sonarQubeService) {
        this.sonarQubeService = sonarQubeService;
    }

    @PostMapping("/query_measures")
    public ResponseEntity<String> queryMeasures(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok(sonarQubeService.queryMeasures(queryProjectRequest));
    }
}
