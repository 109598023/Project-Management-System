package edu.ntut.se1091.team1.pms.entity;

import javax.persistence.*;

@Entity
@Table(name = "project_permission")
public class ProjectPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_permission_id")
    private Long projectPermissionId;

    @ManyToOne
//    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
//    @JoinColumn(name = "role_id")
    private Role role;

    public ProjectPermission() {
    }

    public ProjectPermission(Project project, User user, Role role) {
        this.project = project;
        this.user = user;
        this.role = role;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ProjectPermission{" +
                "project=" + project +
                ", user=" + user +
                ", role=" + role +
                '}';
    }
}
