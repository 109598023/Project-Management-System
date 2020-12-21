package edu.ntut.se1091.team1.pms.dto.request;

import java.util.Collection;

public class AddProjectRequest {

    private String name;

    private String imgUrl;

    private Collection<RepositoryRequest> repositories;

    private String username;

    public AddProjectRequest() {
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

    public void setRepositories(Collection<RepositoryRequest> repositorys) {
        this.repositories = repositorys;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AddProjectRequest{" +
                "name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", repositories=" + repositories +
                ", username='" + username + '\'' +
                '}';
    }
}
