package edu.ntut.se1091.team1.pms.dto.request;

import java.util.List;

public class InviteMembersRequest {

    private Long projectId;

    private String inviterUsername;

    private List<String> inviteesUsername;

    private String roleName;

    public InviteMembersRequest() {
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getInviterUsername() {
        return inviterUsername;
    }

    public void setInviterUsername(String inviterUsername) {
        this.inviterUsername = inviterUsername;
    }

    public List<String> getInviteesUsername() {
        return inviteesUsername;
    }

    public void setInviteesUsername(List<String> inviteesUsername) {
        this.inviteesUsername = inviteesUsername;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isEmpty() {
        return projectId == null || inviterUsername == null
                || inviteesUsername == null || inviteesUsername.isEmpty() || roleName == null;
    }
}
