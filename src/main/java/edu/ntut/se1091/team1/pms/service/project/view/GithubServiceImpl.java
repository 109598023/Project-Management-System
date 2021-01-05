package edu.ntut.se1091.team1.pms.service.project.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.entity.Project;
import edu.ntut.se1091.team1.pms.entity.Repository;
import edu.ntut.se1091.team1.pms.exception.ForbiddenException;
import edu.ntut.se1091.team1.pms.service.project.ProjectService;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.Contributor;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.ContributorsTotalTypeAdapter;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.ContributorsTypeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GithubServiceImpl implements GithubService {

    private static final String IO_EXCEPTION = "IOException";

    private static final String INTERRUPTED_EXCEPTION = "InterruptedException";

    private Logger logger = LoggerFactory.getLogger(GithubServiceImpl.class);

    private ProjectService projectService;

    private String regex = "https://github.com/([^/]+)/([^/]+)(/.*)?";

    private Pattern pattern = Pattern.compile(regex);

    public GithubServiceImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String queryContributors(QueryProjectRequest queryProjectRequest) {
        String url = getApiUrl(queryProjectRequest);
        String responseBody =  getGithubResponse(url + "stats/contributors");
        Type type = new TypeToken<List<Contributor>>() {}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTypeAdapter()).create();
        List<Contributor> contributors = gson.fromJson(responseBody, type);
        return gson.toJson(contributors, type);
    }

    @Override
    public String queryContributorsTotal(QueryProjectRequest queryProjectRequest) {
        String url = getApiUrl(queryProjectRequest);
        String responseBody =  getGithubResponse(url + "stats/contributors");
        Type type = new TypeToken<List<Contributor>>() {}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTotalTypeAdapter()).create();
        List<Contributor> contributors = gson.fromJson(responseBody, type);
        return gson.toJson(contributors, type);
    }

    @Override
    public String queryPunchCard(QueryProjectRequest queryProjectRequest) {
        String url = getApiUrl(queryProjectRequest);
        return getGithubResponse(url + "stats/punch_card");
    }

    @Override
    public String queryIssue(QueryProjectRequest queryProjectRequest) {
        String url = getApiUrl(queryProjectRequest);
        return getGithubResponse(url + "issues");
    }

    private String getGithubResponse(String url) {
        try {

            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            logger.warn(IO_EXCEPTION, e);
        } catch (InterruptedException e) {
            logger.warn(INTERRUPTED_EXCEPTION, e);
            Thread.currentThread().interrupt();
        }
        throw new ForbiddenException();
    }

    private String getApiUrl(QueryProjectRequest queryProjectRequest) {
        Optional<Project> projectOptional = projectService.queryProject(queryProjectRequest);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            for (Repository repository : project.getRepositories()) {
                if (repository.getRepositoryId().equals(queryProjectRequest.getRepositoryId())) {
                    Matcher matcher = pattern.matcher(repository.getUrl());
                    if (matcher.find()) {
                        return "https://api.github.com/repos/" + matcher.group(1) + "/" + matcher.group(2) + "/";
                    }
                }
            }
        }
        return "";
    }
}
