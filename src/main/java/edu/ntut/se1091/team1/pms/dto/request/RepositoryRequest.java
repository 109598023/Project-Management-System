package edu.ntut.se1091.team1.pms.dto.request;

public class RepositoryRequest {

    private String type;

    private String url;

    public RepositoryRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RepositoryRequest{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
