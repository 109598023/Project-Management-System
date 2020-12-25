package edu.ntut.se1091.team1.pms.dto;

public class ProjectRoleDto {

    private String username;

    private String roleName;

    public ProjectRoleDto(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "ProjectRoleDto{" +
                "username='" + username + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
