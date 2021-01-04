package edu.ntut.se1091.team1.pms.util.repository;

import java.net.URL;

public abstract class RepositoryUrl {

    public abstract RepositoryType validate(String url);

    public abstract String getName(String url);

    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
