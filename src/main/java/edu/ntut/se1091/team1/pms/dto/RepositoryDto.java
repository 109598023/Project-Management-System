package edu.ntut.se1091.team1.pms.dto;

public class RepositoryDto {

    private Long repositoryId;

    private String type;

    private String url;

    private String name;

    public RepositoryDto(Long repositoryId, String type, String url, String name) {
        this.repositoryId = repositoryId;
        this.type = type;
        this.url = url;
        this.name = name;
    }

    public Long getRepositoryId() {
        return repositoryId;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RepositoryDto{" +
                "repositoryId=" + repositoryId +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
