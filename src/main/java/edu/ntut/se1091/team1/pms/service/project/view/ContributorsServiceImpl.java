package edu.ntut.se1091.team1.pms.service.project.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.ContributorsTypeAdapter;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.Contributor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
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
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("C:\\Users\\t109598023\\Desktop\\google_googletest_contributors.json"));
            // String data = "[{\"total\":7,\"weeks\":[{\"w\":1605398400,\"a\":1312,\"d\":0,\"c\":2},{\"w\":1606003200,\"a\":23,\"d\":9,\"c\":1},{\"w\":1606608000,\"a\":1160,\"d\":980,\"c\":4},{\"w\":1607212800,\"a\":0,\"d\":0,\"c\":0}],\"author\":{\"login\":\"109598023\",\"id\":70119979,\"node_id\":\"MDQ6VXNlcjcwMTE5OTc5\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/70119979?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/109598023\",\"html_url\":\"https://github.com/109598023\",\"followers_url\":\"https://api.github.com/users/109598023/followers\",\"following_url\":\"https://api.github.com/users/109598023/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/109598023/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/109598023/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/109598023/subscriptions\",\"organizations_url\":\"https://api.github.com/users/109598023/orgs\",\"repos_url\":\"https://api.github.com/users/109598023/repos\",\"events_url\":\"https://api.github.com/users/109598023/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/109598023/received_events\",\"type\":\"User\",\"site_admin\":false}}]";
            Type type = new TypeToken<List<Contributor>>() {}.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(type, new ContributorsTypeAdapter()).create();
            // List<Contributor> contributors = gson.fromJson(data, type);
            List<Contributor> contributors = gson.fromJson(reader, type);
            return gson.toJson(contributors, type);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return "";
        }

        /*
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.github.com/repos/google/googletest/stats/contributors"))
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
        }*/
    }
}
