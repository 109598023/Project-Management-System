package edu.ntut.se1091.team1.pms.entity;

import javax.persistence.*;

@Entity
@Table(name = "repository")
public class Repository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repository_id")
    private Long repositoryId;

    private String type;

    private String url;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    public Repository() {
    }

    public Repository(String type, String url) {
        this.type = type;
        this.url = url;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
