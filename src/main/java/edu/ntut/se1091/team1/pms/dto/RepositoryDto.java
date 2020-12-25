package edu.ntut.se1091.team1.pms.dto;

public class RepositoryDto {

    private String type;

    private String url;

    public RepositoryDto(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RepositoryDto{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
