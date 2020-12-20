package edu.ntut.se1091.team1.pms.service.project;

import edu.ntut.se1091.team1.pms.dto.request.InviteMembersRequest;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.UpdateProjectRequest;
import edu.ntut.se1091.team1.pms.repository.ProjectPermissionRepository;
import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.entity.*;
import edu.ntut.se1091.team1.pms.repository.ProjectRepository;
import edu.ntut.se1091.team1.pms.service.RoleService;
import edu.ntut.se1091.team1.pms.service.UserService;
import edu.ntut.se1091.team1.pms.vo.ProjectVo;
import edu.ntut.se1091.team1.pms.vo.RepositoryVo;
import edu.ntut.se1091.team1.pms.vo.ProjectRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectPermissionRepository projectPermissionRepository;

    @Override
    public Optional<ProjectVo> save(AddProjectRequest addProjectRequest) {
        Optional<User> userOptional = userService.query(addProjectRequest.getUsername());
        if (userOptional.isPresent()) {
            Project project = new Project(addProjectRequest.getName(), addProjectRequest.getImgUrl());
            addProjectRequest.getRepositories().stream()
                    .forEach(r -> project.addRepository(new Repository(r.getType(), r.getUrl())));
            Project project2 = projectRepository.save(project);
            Optional<Role> roleOptional = roleService.queryAndSave("OWNER");
            if (roleOptional.isPresent()) {
                projectPermissionRepository.save(new ProjectPermission(project2, userOptional.get(), roleOptional.get()));
                return Optional.of(new ProjectVo(project2.getProjectId(), project2.getName(), project2.getImgUrl()));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ProjectVo> queryProjectList(QueryProjectRequest queryProjectRequest) {
        List<ProjectVo> projects = new ArrayList<>();
        Optional<User> userOptional = userService.query(queryProjectRequest.getUsername());
        if (userOptional.isPresent()) {
            List<ProjectPermission> projectPermissions = projectPermissionRepository.findByUser(userOptional.get());
            projectPermissions.stream().forEach(p -> {
                Project project = p.getProject();
                projects.add(new ProjectVo(project.getProjectId(), project.getName(), project.getImgUrl()));
            });
        }
        return projects;
    }

    @Override
    public Optional<ProjectVo> queryProject(QueryProjectRequest queryProjectRequest) {
        Optional<User> userOptional = userService.query(queryProjectRequest.getUsername());
        if (userOptional.isPresent()) {
            Optional<Project> projectOptional = projectRepository.findById(queryProjectRequest.getId());
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                Optional<ProjectPermission> projectPermissionOptional
                        = projectPermissionRepository.findByProjectAndUser(project, userOptional.get());
                if (projectPermissionOptional.isPresent()) {
                    List<RepositoryVo> repositories = new ArrayList<>();
                    project.getRepositories().forEach(r -> {
                        repositories.add(new RepositoryVo(r.getType(), r.getUrl()));
                    });
                    return Optional.of(new ProjectVo(project.getProjectId(), project.getName(),
                            project.getImgUrl(), repositories));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProjectVo> update(UpdateProjectRequest updateProjectRequest) {
        Optional<User> userOptional = userService.query(updateProjectRequest.getUsername());
        if (userOptional.isPresent()) {
            Optional<Project> projectOptional = projectRepository.findById(updateProjectRequest.getId());
            if (projectOptional.isPresent()) {
                User user = userOptional.get();
                Project project = projectOptional.get();
                Optional<ProjectPermission> projectPermissionOptional
                        = projectPermissionRepository.findByProjectAndUser(project, user);
                if (projectPermissionOptional.isPresent()) {
                    project.setName(updateProjectRequest.getName());
                    project.setImgUrl(updateProjectRequest.getImgUrl());
                    project.removeAllRepository();
                    updateProjectRequest.getRepositories().forEach(r -> {
                        project.addRepository(new Repository(r.getType(), r.getUrl()));
                    });
                    projectRepository.save(project);
                }
                return Optional.of(new ProjectVo(project.getProjectId(),
                        project.getName(), project.getImgUrl(),
                        project.getRepositories().stream().map(r -> new RepositoryVo(r.getType(), r.getUrl()))
                                .collect(Collectors.toList())));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ProjectRoleVo> queryProjectRoles(QueryProjectRequest queryProjectRequest) {
        List<ProjectRoleVo> roles = new ArrayList<>();
        Optional<Project> projectOptional = projectRepository.findById(queryProjectRequest.getId());
        Project project = projectOptional.get();
        List<ProjectPermission> projectPermissions = projectPermissionRepository.findByProject(project);
        projectPermissions.forEach(p -> {
            String roleName = p.getRole().getName();
            roleName = roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase();
            roles.add(new ProjectRoleVo(p.getUser().getUsername(), roleName));

        });
        return roles;
    }

    @Override
    public List<ProjectRoleVo> inviteMembers(InviteMembersRequest inviteMembersRequest) {
        Optional<User> userOptional = userService.query(inviteMembersRequest.getInviterUsername());
        if (userOptional.isPresent()) {
            Optional<Project> projectOptional = projectRepository.findById(inviteMembersRequest.getProjectId());
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                Optional<ProjectPermission> projectPermissionOptional
                        = projectPermissionRepository.findByProjectAndUser(project, userOptional.get());
                List<ProjectRoleVo> roles = new ArrayList<>();
                if (projectPermissionOptional.isPresent()) {
                    List<User> users = userService.queryAllByUsername(inviteMembersRequest.getInviteesUsername());
                    List<ProjectPermission> projectPermissions = new ArrayList<>();
                    Optional<Role> roleOptional = roleService.queryAndSave(inviteMembersRequest.getRoleName());
                    if (roleOptional.isPresent()) {
                        Role role = roleOptional.get();
                        users.forEach(u -> {
                            projectPermissions.add(new ProjectPermission(project, u, role));
                        });
                        projectPermissionRepository.saveAll(projectPermissions);
                        projectPermissionRepository.findByProject(project).forEach(p -> {
                            String roleName = p.getRole().getName();
                            roleName = roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase();
                            roles.add(new ProjectRoleVo(p.getUser().getUsername(), roleName));
                        });

                    }
                    return roles;
                }
            }
        }
        return List.of();
    }
}
