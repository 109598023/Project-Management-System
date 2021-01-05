package edu.ntut.se1091.team1.pms.controller.project;


import edu.ntut.se1091.team1.pms.dto.ProjectDto;
import edu.ntut.se1091.team1.pms.dto.ProjectRoleDto;
import edu.ntut.se1091.team1.pms.dto.RepositoryDto;
import edu.ntut.se1091.team1.pms.dto.request.*;
import edu.ntut.se1091.team1.pms.entity.Project;
import edu.ntut.se1091.team1.pms.entity.ProjectPermission;
import edu.ntut.se1091.team1.pms.exception.BadRequestException;
import edu.ntut.se1091.team1.pms.exception.ForbiddenException;
import edu.ntut.se1091.team1.pms.service.JWTProvider;
import edu.ntut.se1091.team1.pms.service.project.ProjectService;
import edu.ntut.se1091.team1.pms.service.project.RepositoryUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private RepositoryUrlService repositoryUrlService;

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
        List<ProjectDto> projectDtos = new ArrayList<>();
        List<Project> projects = projectService.queryProjectList(queryProjectRequest);
        for (Project project : projects) {
            projectDtos.add(conversionProjectDto(project));
        }
        return ResponseEntity.ok().body(projectDtos);
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
        List<ProjectRoleDto> projectRoleDtos = new ArrayList<>();
        List<ProjectPermission> projectPermissions = projectService.queryProjectRoles(queryProjectRequest);
        for (ProjectPermission projectPermission : projectPermissions) {
            projectRoleDtos.add(conversionProjectRoleDto(projectPermission));
        }
        return ResponseEntity.ok().body(projectRoleDtos);
    }

    @PostMapping("/invite_members")
    public ResponseEntity<List<ProjectRoleDto>> inviteMembers(@RequestBody InviteMembersRequest inviteMembersRequest) {
        if (inviteMembersRequest.isEmpty()) {
            throw new BadRequestException();
        }
        List<ProjectRoleDto> projectRoleDtos = new ArrayList<>();
        List<ProjectPermission> projectPermissions = projectService.inviteMembers(inviteMembersRequest);
        for (ProjectPermission projectPermission : projectPermissions) {
            projectRoleDtos.add(conversionProjectRoleDto(projectPermission));
        }
        return ResponseEntity.ok().body(projectRoleDtos);
    }

    @PostMapping("/validate_project_url")
    public ResponseEntity<Boolean> validateProjectUrl(@RequestBody RepositoryRequest repositoryRequest) {
        return ResponseEntity.ok().body(repositoryUrlService.validateUrl(repositoryRequest.getUrl()));
    }

    private ProjectDto conversionProjectDto(Project project) {
        return new ProjectDto(project.getProjectId(), project.getName(), project.getImgUrl(),
                project.getRepositories().stream()
                        .map(r -> new RepositoryDto(r.getRepositoryId(), r.getType(), r.getUrl(), r.getName()))
                        .collect(Collectors.toList()));
    }

    private ProjectRoleDto conversionProjectRoleDto(ProjectPermission projectPermission) {
        return new ProjectRoleDto(projectPermission.getUser().getUsername(), projectPermission.getRole().getName());
    }
}

