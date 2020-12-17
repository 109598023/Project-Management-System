package edu.ntut.se1091.team1.pms.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private long projectId;

    private String name;

    private String imgUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Collection<Repository> repositories;

    public Project() {
    }

    public Project(String name, String imgUrl, Collection<Repository> repositories) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.repositories = repositories;
    }

    public long getProjectId() {
        return projectId;
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

    public Collection<Repository> getRepositories() {
        return repositories;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", repositories=" + repositories +
                '}';
    }
}
