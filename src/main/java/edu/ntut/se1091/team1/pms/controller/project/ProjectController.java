package edu.ntut.se1091.team1.pms.controller.project;


import edu.ntut.se1091.team1.pms.dto.ProjectDto;
import edu.ntut.se1091.team1.pms.dto.ProjectRoleDto;
import edu.ntut.se1091.team1.pms.dto.RepositoryDto;
import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.InviteMembersRequest;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.UpdateProjectRequest;
import edu.ntut.se1091.team1.pms.entity.Project;
import edu.ntut.se1091.team1.pms.entity.ProjectPermission;
import edu.ntut.se1091.team1.pms.exception.BadRequestException;
import edu.ntut.se1091.team1.pms.exception.ForbiddenException;
import edu.ntut.se1091.team1.pms.service.JWTProvider;
import edu.ntut.se1091.team1.pms.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/project_view/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("/add_project")
    public ResponseEntity<ProjectDto> addProject(@RequestBody AddProjectRequest addProjectRequest) {
        Optional<Project> projectOptional = projectService.save(addProjectRequest);
        if (projectOptional.isPresent()) {
            return ResponseEntity.ok().body(conversionProjectDto(projectOptional.get()));
        }
        throw new ForbiddenException();
    }

    @PostMapping("/query_project_list")
    public ResponseEntity<List<ProjectDto>> queryProjectList(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(projectService.queryProjectList(queryProjectRequest)
                .stream().map(project -> conversionProjectDto(project)).collect(Collectors.toList()));
    }

    @PostMapping("/query_project")
    public ResponseEntity<ProjectDto> queryProject(@RequestBody QueryProjectRequest queryProjectRequest) {
        Optional<Project> projectOptional = projectService.queryProject(queryProjectRequest);
        if (projectOptional.isPresent()) {
            return ResponseEntity.ok().body(conversionProjectDto(projectOptional.get()));
        }
        throw new ForbiddenException();
    }

    @PostMapping("/update_project")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody UpdateProjectRequest updateProjectRequest) {
        if (updateProjectRequest.isEmpty() || !StringUtils.hasText(updateProjectRequest.getName())) {
            throw new BadRequestException();
        }
        Optional<Project> projectOptional = projectService.update(updateProjectRequest);
        if (projectOptional.isPresent()) {
            return ResponseEntity.ok().body(conversionProjectDto(projectOptional.get()));
        }
        throw new ForbiddenException();
    }

    @PostMapping("/query_project_roles")
    public ResponseEntity<List<ProjectRoleDto>> queryProjectRoles(@RequestBody QueryProjectRequest queryProjectRequest) {
        return ResponseEntity.ok().body(projectService.queryProjectRoles(queryProjectRequest).stream()
                .map(projectPermission -> conversionProjectRoleDto(projectPermission)).collect(Collectors.toList()));
    }

    @PostMapping("/invite_members")
    public ResponseEntity<List<ProjectRoleDto>> inviteMembers(@RequestBody InviteMembersRequest inviteMembersRequest) {
        if (inviteMembersRequest.isEmpty()) {
            throw new BadRequestException();
        }

        return ResponseEntity.ok().body(projectService.inviteMembers(inviteMembersRequest).stream()
                .map(projectPermission -> conversionProjectRoleDto(projectPermission)).collect(Collectors.toList()));
    }

    private ProjectDto conversionProjectDto(Project project) {
        return new ProjectDto(project.getProjectId(), project.getName(), project.getImgUrl(),
                project.getRepositories().stream()
                        .map(r -> new RepositoryDto(r.getType(), r.getUrl())).collect(Collectors.toList()));
    }

    private ProjectRoleDto conversionProjectRoleDto(ProjectPermission projectPermission) {
        return new ProjectRoleDto(projectPermission.getUser().getUsername(), projectPermission.getRole().getName());
    }
}

