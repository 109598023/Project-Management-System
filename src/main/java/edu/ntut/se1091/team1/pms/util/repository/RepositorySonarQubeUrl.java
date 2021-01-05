package edu.ntut.se1091.team1.pms.util.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepositorySonarQubeUrl implements RepositoryUrl {

    private static final String IO_EXCEPTION = "IOException";

    private static final String INTERRUPTED_EXCEPTION = "InterruptedException";

    private Logger logger = LoggerFactory.getLogger(RepositorySonarQubeUrl.class);

    private String regex = "(https?://[^/]+)/.*\\?id=(.*)";

    private Pattern pattern = Pattern.compile(regex);

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
                        .uri(URI.create(matcher.group(1) + "/api/project_links/search?projectKey=" + matcher.group(2)))
                        .timeout(Duration.ofSeconds(10))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.body().contains("links")) {
                    return RepositoryType.SONAR_QUBE;
                }
            }
        } catch (IOException e) {
            logger.warn(IO_EXCEPTION, e);
        } catch (InterruptedException e) {
            logger.warn(INTERRUPTED_EXCEPTION, e);
            Thread.currentThread().interrupt();
        }
        return RepositoryType.NONE;
    }

    @Override
    public String getName(String url) {
        try {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                HttpClient httpClient = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(matcher.group(1) + "/api/components/show?component=" + matcher.group(2)))
                        .timeout(Duration.ofSeconds(10))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                JsonObject convertedObject = new Gson().fromJson(response.body(), JsonObject.class);
                if (convertedObject.isJsonObject()) {
                    JsonElement componentElement = convertedObject.get("component");
                    if (componentElement.isJsonObject()) {
                        JsonElement element = componentElement.getAsJsonObject().get("name");
                        if (element != null) {
                            return element.getAsString();
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.warn(IO_EXCEPTION, e);
        } catch (InterruptedException e) {
            logger.warn(INTERRUPTED_EXCEPTION, e);
            Thread.currentThread().interrupt();
        }
        return "";
    }
}
