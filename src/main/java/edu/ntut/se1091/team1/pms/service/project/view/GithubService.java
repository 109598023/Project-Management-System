package edu.ntut.se1091.team1.pms.service.project.view;


import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;

public interface GithubService {

   String queryContributors(QueryProjectRequest queryProjectRequest);

   String queryContributorsTotal(QueryProjectRequest queryProjectRequest);

   String queryPunchCard(QueryProjectRequest queryProjectRequest);
}
