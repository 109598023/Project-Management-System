package edu.ntut.se1091.team1.pms.util.repository;

import java.net.URL;

public interface RepositoryUrl {

    RepositoryType validate(String url);

    String getName(String url);

    static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
