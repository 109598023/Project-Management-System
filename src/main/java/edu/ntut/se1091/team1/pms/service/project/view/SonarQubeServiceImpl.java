package edu.ntut.se1091.team1.pms.service.project.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.Contributor;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.ContributorsTypeAdapter;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.sonarqube.Measure;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.sonarqube.MeasuresTypeAdapter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Service
public class SonarQubeServiceImpl implements SonarQubeService {

    @Override
    public String queryMeasures() {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:9000/api/measures/component?component=edu.ntut.se1091.team1%3Apms&metricKeys=bugs,vulnerabilities,security_hotspots,code_smells,reliability_rating,security_rating,security_review_rating,sqale_rating"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders headers = response.headers();
            Type type = new TypeToken<List<Measure>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new MeasuresTypeAdapter()).create();
            List<Measure> measures = gson.fromJson(response.body(), type);
            return gson.toJson(measures, type);
        } catch (IOException | InterruptedException e) {
            return "";
        }
    }
}
