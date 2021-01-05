package edu.ntut.se1091.team1.pms.service.project.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.entity.Project;
import edu.ntut.se1091.team1.pms.entity.Repository;
import edu.ntut.se1091.team1.pms.exception.ForbiddenException;
import edu.ntut.se1091.team1.pms.service.project.ProjectService;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.sonarqube.Measure;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.sonarqube.MeasuresTypeAdapter;
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
public class SonarQubeServiceImpl implements SonarQubeService {

    private static final String IO_EXCEPTION = "IOException";

    private static final String INTERRUPTED_EXCEPTION = "InterruptedException";

    private Logger logger = LoggerFactory.getLogger(SonarQubeServiceImpl.class);

    private ProjectService projectService;

    private String regex = "(https?://[^/]+)/.*\\?id=(.*)";

    private Pattern pattern = Pattern.compile(regex);

    public SonarQubeServiceImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String queryMeasures(QueryProjectRequest queryProjectRequest) {
        try {
            String url = getMeasuresApiUrl(queryProjectRequest);
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
            Type type = new TypeToken<List<Measure>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new MeasuresTypeAdapter()).create();
            List<Measure> measures = gson.fromJson(response.body(), type);
            return gson.toJson(measures, type);
        } catch (IOException e) {
            logger.warn(IO_EXCEPTION, e);
        } catch (InterruptedException e) {
            logger.warn(INTERRUPTED_EXCEPTION, e);
            Thread.currentThread().interrupt();
        }
        throw new ForbiddenException();
    }

    private String getMeasuresApiUrl(QueryProjectRequest queryProjectRequest) {
        Optional<Project> projectOptional = projectService.queryProject(queryProjectRequest);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            for (Repository repository : project.getRepositories()) {
                if (repository.getRepositoryId().equals(queryProjectRequest.getRepositoryId())) {
                    Matcher matcher = pattern.matcher(repository.getUrl());
                    if (matcher.find()) {
                        return matcher.group(1) + "/api/measures/component?component=" + matcher.group(2) + "&metricKeys=bugs,vulnerabilities,security_hotspots,code_smells,reliability_rating,security_rating,security_review_rating,sqale_rating";
                    }
                }
            }
        }
        return "";
    }
}
