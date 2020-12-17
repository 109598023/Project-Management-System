package edu.ntut.se1091.team1.pms.repository;

import edu.ntut.se1091.team1.pms.entity.Project;
import edu.ntut.se1091.team1.pms.entity.ProjectPermission;
import edu.ntut.se1091.team1.pms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPermissionRepository extends JpaRepository<ProjectPermission, Long> {

    ProjectPermission findByProject(Project project);

    List<ProjectPermission> findByUser(User user);

    ProjectPermission findByProjectAndUser(Project project, User user);
}
