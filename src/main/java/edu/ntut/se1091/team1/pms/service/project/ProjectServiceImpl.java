package edu.ntut.se1091.team1.pms.service.project;

import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.UpdateProjectRequest;
import edu.ntut.se1091.team1.pms.repository.ProjectPermissionRepository;
import edu.ntut.se1091.team1.pms.repository.RoleRepository;
import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.entity.*;
import edu.ntut.se1091.team1.pms.repository.ProjectRepository;
import edu.ntut.se1091.team1.pms.service.UserService;
import edu.ntut.se1091.team1.pms.vo.ProjectVo;
import edu.ntut.se1091.team1.pms.vo.RepositoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectPermissionRepository projectPermissionRepository;

    @Override
    public ProjectVo save(AddProjectRequest addProjectRequest) {
        List<Repository> repositories = new ArrayList<>();
        Project project = new Project(addProjectRequest.getName(), addProjectRequest.getImgUrl());
        addProjectRequest.getRepositorys().stream()
                .forEach(r -> project.addRepository(new Repository(r.getType(), r.getUrl())));
        User user = userService.query("test");
        Project project2 = projectRepository.save(project);
        Role role = roleRepository.findByName("OWNER");
        if (role == null) {
            role = roleRepository.save(new Role("OWNER"));
        }
        ProjectPermission projectPermission = projectPermissionRepository.save(new ProjectPermission(project2, user, role));
        return new ProjectVo(project2.getProjectId(), project2.getName(), project2.getImgUrl());
    }

    @Override
    public List<ProjectVo> queryProjectList(QueryProjectRequest queryProjectRequest) {
        List<ProjectVo> projects = new ArrayList<>();
        User user = userService.query(queryProjectRequest.getUsername());
        List<ProjectPermission> projectPermissions = projectPermissionRepository.findByUser(user);
        projectPermissions.stream().forEach(p -> {
            Project project = p.getProject();
            projects.add(new ProjectVo(project.getProjectId(), project.getName(), project.getImgUrl()));
        });
        return projects;
    }

    @Override
    public ProjectVo queryProject(QueryProjectRequest queryProjectRequest) {
        ProjectVo projectVo = null;
        Optional<Project> projectOptional = projectRepository.findById(queryProjectRequest.getId());
        Project project = projectOptional.get();
        User user = userService.query(queryProjectRequest.getUsername());
        ProjectPermission projectPermission = projectPermissionRepository.findByProjectAndUser(project, user);
        if (projectPermission != null) {
            List<RepositoryVo> repositories = new ArrayList<>();
            project.getRepositories().forEach(r -> {
                repositories.add(new RepositoryVo(r.getType(), r.getUrl()));
            });
            projectVo = new ProjectVo(project.getProjectId(), project.getName(), project.getImgUrl(), repositories);
        }
        return projectVo;
    }

    @Override
    public ProjectVo update(UpdateProjectRequest updateProjectRequest) {
        Optional<Project> projectOptional = projectRepository.findById(updateProjectRequest.getId());
        Project project = projectOptional.get();
        User user = userService.query(updateProjectRequest.getUsername());
        ProjectPermission projectPermission = projectPermissionRepository.findByProjectAndUser(project, user);
        if (projectPermission != null) {
            project.setName(updateProjectRequest.getName());
            project.setImgUrl(updateProjectRequest.getImgUrl());
            project.removeAllRepository();
            updateProjectRequest.getRepositories().forEach(r -> {
                project.addRepository(new Repository(r.getType(), r.getUrl()));
            });
            projectRepository.save(project);
        }
        return new ProjectVo(project.getProjectId(), project.getName(), project.getImgUrl(), project.getRepositories().stream().map(r -> new RepositoryVo(r.getType(), r.getUrl())).collect(Collectors.toList()));
    }
}
