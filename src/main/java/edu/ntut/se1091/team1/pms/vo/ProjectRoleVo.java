package edu.ntut.se1091.team1.pms.vo;

public class ProjectRoleVo {

    private String username;

    private String roleName;

    public ProjectRoleVo(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public String getRoleName() {
        return roleName;
    }
}
