package edu.ntut.se1091.team1.pms.dto.request;

public class RepositoryRequest {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RepositoryRequest{" +
                "url='" + url + '\'' +
                '}';
    }
}
