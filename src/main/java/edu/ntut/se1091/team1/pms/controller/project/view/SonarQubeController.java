package edu.ntut.se1091.team1.pms.controller.project.view;

import edu.ntut.se1091.team1.pms.service.project.view.SonarQubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/project_view/sonar_qube", produces = MediaType.APPLICATION_JSON_VALUE)
public class SonarQubeController {

    @Autowired
    private SonarQubeService sonarQubeService;

    @PostMapping("/query_measures")
    public ResponseEntity<String> queryMeasures() {
        return ResponseEntity.ok(sonarQubeService.queryMeasures());
    }
}
