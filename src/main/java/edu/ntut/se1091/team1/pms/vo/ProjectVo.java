package edu.ntut.se1091.team1.pms.vo;

import java.util.Collection;
import java.util.List;

public class ProjectVo {

    private Long id;

    private String name;

    private String imgUrl;

    private Collection<RepositoryVo> repositorys;

    public ProjectVo(Long id, String name, String imgUrl) {
        this(id, name, imgUrl, List.of());
    }

    public ProjectVo(Long id, String name, String imgUrl, Collection<RepositoryVo> repositorys) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.repositorys = repositorys;
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

    public Collection<RepositoryVo> getRepositorys() {
        return repositorys;
    }

    @Override
    public String toString() {
        return "ProjectVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", repositorys=" + repositorys +
                '}';
    }
}
