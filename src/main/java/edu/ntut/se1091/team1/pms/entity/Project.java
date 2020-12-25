package edu.ntut.se1091.team1.pms.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private long projectId;

    private String name;

    private String imgUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private List<Repository> repositories = new ArrayList<>();

    public Project() {
    }

    public Project(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
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

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public void addRepository(Repository repository) {
        repository.setProject(this);
        repositories.add(repository);
    }

    public void addRepositories(List<Repository> repositories) {
        repositories.forEach(repository -> repository.setProject(this));
        this.repositories.addAll(repositories);
    }

    public void removeRepository(Repository repository) {
        repositories.remove(repository);
        repository.setProject(null);
    }

    public void removeRepositories() {
        repositories.forEach(repository -> repository.setProject(null));
        repositories.clear();
    }
}
