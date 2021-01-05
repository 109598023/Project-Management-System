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
import edu.ntut.se1091.team1.pms.util.repository.RepositoryType;
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

    @Autowired
    private RepositoryUrlService repositoryUrlService;

    @Override
    public Optional<Project> save(AddProjectRequest addProjectRequest) {
        Optional<User> optionalUser = userService.queryUsername(addProjectRequest.getUsername());
        if (optionalUser.isPresent()) {
            Project project = new Project(addProjectRequest.getName(), addProjectRequest.getImgUrl());
            List<Repository> repositories = new ArrayList<>();
            addProjectRequest.getRepositories().forEach(r -> {
                RepositoryType type = repositoryUrlService.getUrlType(r.getUrl());

                if (type != RepositoryType.NONE) {
                    String repositoryName = repositoryUrlService.getName(type, r.getUrl());
                    repositories.add(new Repository(type.getName(), r.getUrl(), repositoryName));
                }
            });
            project.addRepositories(repositories);

            Optional<Role> roleOptional = roleService.queryAndSave("Owner");
            if (roleOptional.isPresent()) {
                project = projectRepository.save(project);
                projectPermissionRepository.save(new ProjectPermission(project, optionalUser.get(), roleOptional.get()));
                return Optional.of(project);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Project> queryProjectList(QueryProjectRequest queryProjectRequest) {
        List<Project> projects = new ArrayList<>();
        Optional<User> userOptional = userService.queryUsername(queryProjectRequest.getUsername());
        if (userOptional.isPresent()) {
            List<ProjectPermission> projectPermissions = projectPermissionRepository.findByUser(userOptional.get());
            for (ProjectPermission projectPermission : projectPermissions) {
                projects.add(projectPermission.getProject());
            }
        }
        return projects;
    }

    @Override
    public Optional<Project> queryProject(QueryProjectRequest queryProjectRequest) {
        Optional<User> userOptional = userService.queryUsername(queryProjectRequest.getUsername());
        Optional<Project> projectOptional = projectRepository.findById(queryProjectRequest.getId());
        if (userOptional.isPresent() && projectOptional.isPresent()) {
            Optional<ProjectPermission> projectPermissionOptional
                    = projectPermissionRepository.findByProjectAndUser(projectOptional.get(), userOptional.get());
            if (projectPermissionOptional.isPresent()) {
                return projectOptional;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Project> update(UpdateProjectRequest updateProjectRequest) {
        Optional<User> userOptional = userService.queryUsername(updateProjectRequest.getUsername());
        Optional<Project> projectOptional = projectRepository.findById(updateProjectRequest.getId());

        if (userOptional.isPresent() && projectOptional.isPresent()) {
                User user = userOptional.get();
                Project project = projectOptional.get();
                Optional<ProjectPermission> projectPermissionOptional
                        = projectPermissionRepository.findByProjectAndUser(project, user);
                if (projectPermissionOptional.isPresent()) {
                    project.setName(updateProjectRequest.getName());
                    project.setImgUrl(updateProjectRequest.getImgUrl());
                    project.removeRepositories();
                    List<Repository> repositories = new ArrayList<>();
                    updateProjectRequest.getRepositories().forEach(r -> {
                        RepositoryType type = repositoryUrlService.getUrlType(r.getUrl());
                        if (type != RepositoryType.NONE) {
                            String repositoryName = repositoryUrlService.getName(type, r.getUrl());
                            repositories.add(new Repository(type.getName(), r.getUrl(), repositoryName));
                        }
                    });
                    project.addRepositories(repositories);
                    return Optional.of(projectRepository.save(project));
                }
        }
        return Optional.empty();
    }

    @Override
    public List<ProjectPermission> queryProjectRoles(QueryProjectRequest queryProjectRequest) {
        Optional<Project> projectOptional = projectRepository.findById(queryProjectRequest.getId());
        if (projectOptional.isPresent()) {
            return projectPermissionRepository.findByProject(projectOptional.get());
        }
        return List.of();
    }

    @Override
    public List<ProjectPermission> inviteMembers(InviteMembersRequest inviteMembersRequest) {
        List<ProjectPermission> projectPermissions = List.of();
        Optional<User> userOptional = userService.queryUsername(inviteMembersRequest.getInviterUsername());
        Optional<Project> projectOptional = projectRepository.findById(inviteMembersRequest.getProjectId());

        if (userOptional.isPresent() && projectOptional.isPresent()) {
                Project project = projectOptional.get();
                Optional<ProjectPermission> projectPermissionOptional
                        = projectPermissionRepository.findByProjectAndUser(project, userOptional.get());
                if (projectPermissionOptional.isPresent()) {
                    List<User> users = userService.queryAllByUsername(inviteMembersRequest.getInviteesUsername());
                    Optional<Role> roleOptional = roleService.queryAndSave(inviteMembersRequest.getRoleName());
                    if (roleOptional.isPresent()) {
                        Role role = roleOptional.get();
                        projectPermissions = users.stream().map(user -> new ProjectPermission(project, user, role))
                                .collect(Collectors.toList());
                        projectPermissionRepository.saveAll(projectPermissions);
                        projectPermissions = projectPermissionRepository.findByProject(project);
                    }
                }
        }
        return projectPermissions;
    }
}
