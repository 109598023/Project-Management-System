package edu.ntut.se1091.team1.pms.util.gson.typeadapter.github;

public class Author {

    private String login;

    private String avatarUrl;

    private String htmlUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    @Override
    public String toString() {
        return "Author{" +
                "login='" + login + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                '}';
    }
}
