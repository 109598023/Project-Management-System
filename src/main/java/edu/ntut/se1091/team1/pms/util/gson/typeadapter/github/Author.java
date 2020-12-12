package edu.ntut.se1091.team1.pms.util.gson.typeadapter.github;

public class Author {

    private String login;

    private String avatarUrl;

    public Author() {
    }

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

    @Override
    public String toString() {
        return "Author{" +
                "login='" + login + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
