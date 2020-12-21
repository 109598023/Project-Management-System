package edu.ntut.se1091.team1.pms.vo;

public class RepositoryVo {

    private String type;

    private String url;

    public RepositoryVo(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RepositoryVo{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
