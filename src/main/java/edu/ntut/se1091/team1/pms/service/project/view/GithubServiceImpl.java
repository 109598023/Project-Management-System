package edu.ntut.se1091.team1.pms.service.project.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import edu.ntut.se1091.team1.pms.dto.request.QueryProjectRequest;
import edu.ntut.se1091.team1.pms.exception.ForbiddenException;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.Contributor;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.ContributorsTotalTypeAdapter;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.ContributorsTypeAdapter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Service
public class GithubServiceImpl implements GithubService {


    public GithubServiceImpl() {
    }

    @Override
    public String queryContributors(QueryProjectRequest queryProjectRequest) {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.github.com/repos/109598023/Project-Management-System-Backend/stats/contributors"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Type type = new TypeToken<List<Contributor>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTypeAdapter()).create();
            List<Contributor> contributors = gson.fromJson(response.body(), type);
            return gson.toJson(contributors, type);
        } catch (IOException | InterruptedException e) {
            throw new ForbiddenException();
        }
    }

    @Override
    public String queryContributorsTotal(QueryProjectRequest queryProjectRequest) {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.github.com/repos/109598023/Project-Management-System-Backend/stats/contributors"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Type type = new TypeToken<List<Contributor>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTotalTypeAdapter()).create();
            List<Contributor> contributors = gson.fromJson(response.body(), type);
            return gson.toJson(contributors, type);
        } catch (IOException | InterruptedException e) {
            throw new ForbiddenException();
        }
    }

    @Override
    public String queryPunchCard(QueryProjectRequest queryProjectRequest) {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.github.com/repos/109598023/Project-Management-System-Backend/stats/punch_card"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ForbiddenException();
        }
    }

    @Override
    public String queryIssue(QueryProjectRequest queryProjectRequest) {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.github.com/repos/109598023/Project-Management-System-Backend/issues"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ForbiddenException();
        }
    }

    private List<Contributor> queryContributor(QueryProjectRequest queryProjectRequest, TypeAdapter typeAdapter) {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("C:\\Users\\t109598023\\Desktop\\google_googletest_contributors.json"));
            // String data = "[{\"total\":7,\"weeks\":[{\"w\":1605398400,\"a\":1312,\"d\":0,\"c\":2},{\"w\":1606003200,\"a\":23,\"d\":9,\"c\":1},{\"w\":1606608000,\"a\":1160,\"d\":980,\"c\":4},{\"w\":1607212800,\"a\":0,\"d\":0,\"c\":0}],\"author\":{\"login\":\"109598023\",\"id\":70119979,\"node_id\":\"MDQ6VXNlcjcwMTE5OTc5\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/70119979?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/109598023\",\"html_url\":\"https://github.com/109598023\",\"followers_url\":\"https://api.github.com/users/109598023/followers\",\"following_url\":\"https://api.github.com/users/109598023/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/109598023/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/109598023/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/109598023/subscriptions\",\"organizations_url\":\"https://api.github.com/users/109598023/orgs\",\"repos_url\":\"https://api.github.com/users/109598023/repos\",\"events_url\":\"https://api.github.com/users/109598023/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/109598023/received_events\",\"type\":\"User\",\"site_admin\":false}}]";
            Type type = new TypeToken<List<Contributor>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTypeAdapter()).create();
            // List<Contributor> contributors = gson.fromJson(data, type);
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
        }
        return List.of();
/*
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.github.com/repos/google/googletest/stats/contributors"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Type type = new TypeToken<List<Contributor>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTypeAdapter()).create();
            List<Contributor> contributors = gson.fromJson(response.body(), type);
            return gson.toJson(contributors, type);
        } catch (IOException | InterruptedException e) {
            throw new ForbiddenException();
        }
 */
    }
}
