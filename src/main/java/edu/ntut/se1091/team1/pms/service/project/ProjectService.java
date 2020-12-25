package edu.ntut.se1091.team1.pms.service.project;

import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.InviteMembersRequest;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.UpdateProjectRequest;
import edu.ntut.se1091.team1.pms.entity.Project;
import edu.ntut.se1091.team1.pms.entity.ProjectPermission;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<Project> save(AddProjectRequest addProjectRequest);

    List<Project> queryProjectList(QueryProjectRequest queryProjectRequest);

    Optional<Project> queryProject(QueryProjectRequest queryProjectRequest);

    Optional<Project> update(UpdateProjectRequest updateProjectRequest);

    List<ProjectPermission> queryProjectRoles(QueryProjectRequest queryProjectRequest);

    List<ProjectPermission> inviteMembers(InviteMembersRequest inviteMembersRequest);
}
