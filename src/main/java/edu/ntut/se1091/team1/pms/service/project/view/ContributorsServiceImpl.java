package edu.ntut.se1091.team1.pms.service.project.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.ContributorsTypeAdapter;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.Contributor;
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
public class ContributorsServiceImpl implements ContributorsService{


    public ContributorsServiceImpl() {
    }

    @Override
    public String queryContributors(long id) {
        return "";
    }

    @Override
    public String queryContributors(String url) {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.github.com/repositories/314194834/stats/contributors"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders headers = response.headers();
            Type type = new TypeToken<List<Contributor>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTypeAdapter()).create();
            List<Contributor> contributors = gson.fromJson(response.body(), type);
            return gson.toJson(contributors, type);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
