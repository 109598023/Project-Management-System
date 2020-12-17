package edu.ntut.se1091.team1.pms.service.project;

import edu.ntut.se1091.team1.pms.dto.request.AddProjectRequest;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.vo.ProjectVo;

import java.util.List;

public interface ProjectService {

    ProjectVo save(AddProjectRequest addProjectRequest);

    List<ProjectVo> queryProjectList(QueryProjectRequest queryProjectRequest);

    ProjectVo queryProject(QueryProjectRequest queryProjectRequest);
}
