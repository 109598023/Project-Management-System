package edu.ntut.se1091.team1.pms.service.project;

import edu.ntut.se1091.team1.pms.util.repository.RepositoryType;
import edu.ntut.se1091.team1.pms.util.repository.RepositoryGithubUrl;
import edu.ntut.se1091.team1.pms.util.repository.RepositorySonarQubeUrl;
import edu.ntut.se1091.team1.pms.util.repository.RepositoryUrl;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class RepositoryUrlServiceImpl implements RepositoryUrlService {

    private final Map<RepositoryType, RepositoryUrl> validateUrlMap;

    public RepositoryUrlServiceImpl() {
        validateUrlMap = new EnumMap<>(RepositoryType.class);
        setValidateUrlMap();
    }

    private void setValidateUrlMap() {
        validateUrlMap.put(RepositoryType.GITHUB, new RepositoryGithubUrl());
        validateUrlMap.put(RepositoryType.SONAR_QUBE, new RepositorySonarQubeUrl());

    }

    @Override
    public RepositoryType getUrlType(String url) {
        if (RepositoryUrl.isValidUrl(url)) {
            RepositoryType type;
            for (RepositoryUrl validateUrl : validateUrlMap.values()) {
                type = validateUrl.validate(url);
                if (type != RepositoryType.NONE) {
                    return type;
                }
            }
        }
        return RepositoryType.NONE;
    }

    @Override
    public boolean validateUrl(String url) {
        return getUrlType(url) != RepositoryType.NONE;
    }

    @Override
    public String getName(RepositoryType type, String url) {
        if (type == RepositoryType.NONE) {
            return "";
        }
        return validateUrlMap.get(type).getName(url);
    }
}
