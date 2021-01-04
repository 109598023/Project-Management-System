package edu.ntut.se1091.team1.pms.service.project;

import edu.ntut.se1091.team1.pms.dto.request.RepositoryRequest;
import edu.ntut.se1091.team1.pms.util.repository.RepositoryType;


public interface RepositoryUrlService {

    RepositoryType getUrlType(String url);

    boolean validateUrl(String url);

    String getName(RepositoryType type, String url);
}
