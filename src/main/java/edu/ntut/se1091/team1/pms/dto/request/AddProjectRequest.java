package edu.ntut.se1091.team1.pms.dto.request;

import java.util.Collection;

public class AddProjectRequest {

    private String name;

    private String imgUrl;

    private Collection<RepositoryRequest> repositorys;

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

    public Collection<RepositoryRequest> getRepositorys() {
        return repositorys;
    }

    public void setRepositorys(Collection<RepositoryRequest> repositorys) {
        this.repositorys = repositorys;
    }

    @Override
    public String toString() {
        return "ProjectRequest{" +
                "name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", repositorys=" + repositorys +
                '}';
    }
}
