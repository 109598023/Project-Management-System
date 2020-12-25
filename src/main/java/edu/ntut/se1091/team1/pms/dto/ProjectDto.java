package edu.ntut.se1091.team1.pms.dto;

import java.util.Collection;

public class ProjectDto {

    private Long id;

    private String name;

    private String imgUrl;

    private Collection<RepositoryDto> repositories;

    public ProjectDto(Long id, String name, String imgUrl, Collection<RepositoryDto> repositories) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.repositories = repositories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Collection<RepositoryDto> getRepositories() {
        return repositories;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", repositories=" + repositories +
                '}';
    }
}
