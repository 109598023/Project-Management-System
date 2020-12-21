package edu.ntut.se1091.team1.pms.dto.request;

public class QueryProjectRequest {

    private Long id;

    private String username;

    public QueryProjectRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "QueryProjectRequest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
