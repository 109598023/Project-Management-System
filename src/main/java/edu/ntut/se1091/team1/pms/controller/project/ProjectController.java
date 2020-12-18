package edu.ntut.se1091.team1.pms.controller.project;


import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.UpdateProjectRequest;
import edu.ntut.se1091.team1.pms.exception.BadRequestException;
import edu.ntut.se1091.team1.pms.exception.ForbiddenException;
import edu.ntut.se1091.team1.pms.service.JWTProvider;
import edu.ntut.se1091.team1.pms.service.project.ProjectService;
import edu.ntut.se1091.team1.pms.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/api/project_view/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("/add_project")
    public ResponseEntity<ProjectVo> addProject(@RequestBody AddProjectRequest addProjectRequest) {
        return ResponseEntity.ok().body(projectService.save(addProjectRequest));
    }

    @PostMapping("/query_project_list")
    public ResponseEntity<List<ProjectVo>> queryProjectList(@RequestBody QueryProjectRequest queryProjectRequest, HttpServletRequest request) {
        List<ProjectVo> projects = List.of();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader)) {
            String accessToken = authHeader.replace("Bearer ", "");
            if (jwtProvider.validateAccessToken(accessToken)) {
                String username = jwtProvider.getUsernameFromToken(accessToken);
                if (username.equals(queryProjectRequest.getUsername())) {
                    projects = projectService.queryProjectList(queryProjectRequest);
                }
            }
        }
        return ResponseEntity.ok().body(projects);
    }

    @PostMapping("/query_project")
    public ResponseEntity<ProjectVo> queryProject(@RequestBody QueryProjectRequest queryProjectRequest, HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && queryProjectRequest.getId() != null) {
            String accessToken = authHeader.replace("Bearer ", "");
            if (jwtProvider.validateAccessToken(accessToken)) {
                String username = jwtProvider.getUsernameFromToken(accessToken);
                if (username.equals(queryProjectRequest.getUsername())) {
                    return ResponseEntity.ok().body(projectService.queryProject(queryProjectRequest));
                }
            }
        }
        throw new ForbiddenException();
    }

    @PostMapping("/check_permission")
    public ResponseEntity checkPermission(@RequestBody QueryProjectRequest queryProjectRequest, HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && queryProjectRequest.getId() != null) {
            String accessToken = authHeader.replace("Bearer ", "");
            if (jwtProvider.validateAccessToken(accessToken)) {
                String username = jwtProvider.getUsernameFromToken(accessToken);
                if (username.equals(queryProjectRequest.getUsername())) {
                    return ResponseEntity.ok().body(projectService.queryProject(queryProjectRequest));
                }
            }
        }
        throw new ForbiddenException();
    }

    @PostMapping("/update_project")
    public ResponseEntity updateProject(@RequestBody UpdateProjectRequest updateProjectRequest, HttpServletRequest request) {
        if (updateProjectRequest.isEmpty() || !StringUtils.hasText(updateProjectRequest.getName())) {
            throw new BadRequestException();
        }
        return ResponseEntity.ok().build();
    }
}

