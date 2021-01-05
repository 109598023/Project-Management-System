package edu.ntut.se1091.team1.pms.service.project.view;


import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;

public interface SonarQubeService {

    String queryMeasures(QueryProjectRequest queryProjectRequest);
}
