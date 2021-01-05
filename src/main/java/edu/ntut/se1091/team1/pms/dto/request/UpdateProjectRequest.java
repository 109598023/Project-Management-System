package edu.ntut.se1091.team1.pms.dto.request;


import java.util.Collection;

public class UpdateProjectRequest {

    private Long id;

    private String name;

    private String imgUrl;

    private Collection<RepositoryRequest> repositories;

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Collection<RepositoryRequest> getRepositories() {
        return repositories;
    }

    public void setRepositories(Collection<RepositoryRequest> repositories) {
        this.repositories = repositories;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEmpty() {
        return id == null || username == null || name == null || imgUrl == null || repositories == null;
    }

    @Override
    public String toString() {
        return "UpdateProjectRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", repositories=" + repositories +
                ", username='" + username + '\'' +
                '}';
    }
}
