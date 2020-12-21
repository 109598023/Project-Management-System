package edu.ntut.se1091.team1.pms.controller.project;


import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.InviteMembersRequest;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.UpdateProjectRequest;
import edu.ntut.se1091.team1.pms.exception.BadRequestException;
import edu.ntut.se1091.team1.pms.exception.ForbiddenException;
import edu.ntut.se1091.team1.pms.service.JWTProvider;
import edu.ntut.se1091.team1.pms.service.project.ProjectService;
import edu.ntut.se1091.team1.pms.vo.ProjectRoleVo;
import edu.ntut.se1091.team1.pms.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/project_view/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("/add_project")
    public ResponseEntity<ProjectVo> addProject(@RequestBody AddProjectRequest addProjectRequest) {
        Optional<ProjectVo> projectVoOptional = projectService.save(addProjectRequest);
        if (projectVoOptional.isPresent()) {
            return ResponseEntity.ok().body(projectVoOptional.get());
        }
        throw new ForbiddenException();
    }

    @PostMapping("/query_project_list")
    public ResponseEntity<List<ProjectVo>> queryProjectList(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(projectService.queryProjectList(queryProjectRequest));
    }

    @PostMapping("/query_project")
    public ResponseEntity<ProjectVo> queryProject(@RequestBody QueryProjectRequest queryProjectRequest) {
        Optional<ProjectVo> projectVoOptional = projectService.queryProject(queryProjectRequest);
        if (projectVoOptional.isPresent()) {
            return ResponseEntity.ok().body(projectVoOptional.get());
        }
        throw new ForbiddenException();
    }

    @PostMapping("/update_project")
    public ResponseEntity<ProjectVo> updateProject(@RequestBody UpdateProjectRequest updateProjectRequest) {
        if (updateProjectRequest.isEmpty() || !StringUtils.hasText(updateProjectRequest.getName())) {
            throw new BadRequestException();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/query_project_roles")
    public ResponseEntity<List<ProjectRoleVo>> queryProjectRoles(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(projectService.queryProjectRoles(queryProjectRequest));
    }

    @PostMapping("/invite_members")
    public ResponseEntity<List<ProjectRoleVo>> inviteMembers(@RequestBody InviteMembersRequest inviteMembersRequest) {
        if (inviteMembersRequest.isEmpty()) {
            throw new BadRequestException();
        }
        return ResponseEntity.ok().body(projectService.inviteMembers(inviteMembersRequest));
    }
}

