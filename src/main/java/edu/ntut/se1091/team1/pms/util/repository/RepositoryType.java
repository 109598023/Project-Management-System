package edu.ntut.se1091.team1.pms.util.repository;

public enum RepositoryType {

    NONE("None"),
    GITHUB("Github"),
    SONAR_QUBE("SonarQube");

    private RepositoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static RepositoryType getType(String name) {
        for (RepositoryType type : values()) {
            if (name.equals(type.getName())) {
                return type;
            }
        }
        return RepositoryType.NONE;
    }

    public String getName() {
        return name;
    }

    private String name;
}
