package edu.ntut.se1091.team1.pms.service.project;

import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.InviteMembersRequest;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.UpdateProjectRequest;
import edu.ntut.se1091.team1.pms.vo.ProjectVo;
import edu.ntut.se1091.team1.pms.vo.ProjectRoleVo;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<ProjectVo> save(AddProjectRequest addProjectRequest);

    List<ProjectVo> queryProjectList(QueryProjectRequest queryProjectRequest);

    Optional<ProjectVo> queryProject(QueryProjectRequest queryProjectRequest);

    Optional<ProjectVo> update(UpdateProjectRequest updateProjectRequest);

    List<ProjectRoleVo> queryProjectRoles(QueryProjectRequest queryProjectRequest);

    List<ProjectRoleVo> inviteMembers(InviteMembersRequest inviteMembersRequest);
}
