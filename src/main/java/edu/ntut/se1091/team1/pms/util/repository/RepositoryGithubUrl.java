package edu.ntut.se1091.team1.pms.util.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepositoryGithubUrl extends RepositoryUrl {

    String regex = "https://github.com/([^/]+)/([^/]+)(/.*)?";
    Pattern pattern = Pattern.compile(regex);

    @Override
    public RepositoryType validate(String url) {
        try {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                HttpClient httpClient = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://api.github.com/repos/" + matcher.group(1) + "/" + matcher.group(2) + "/languages"))
                        .timeout(Duration.ofSeconds(10))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (!response.body().contains("Not Found")) {
                    return RepositoryType.GITHUB;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return RepositoryType.NONE;
    }

    @Override
    public String getName(String url) {
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }
}
