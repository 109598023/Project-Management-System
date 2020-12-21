package edu.ntut.se1091.team1.pms.util.gson.typeadapter.github;

import java.util.List;

public class Contributor {

    private int total;

    private List<String> weeks;

    private List<Integer> additions;

    private List<Integer> deletions;

    private List<Integer> commits;

    private Author author;

    public Contributor() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    public List<Integer> getAdditions() {
        return additions;
    }

    public void setAdditions(List<Integer> additions) {
        this.additions = additions;
    }

    public List<Integer> getDeletions() {
        return deletions;
    }

    public void setDeletions(List<Integer> deletions) {
        this.deletions = deletions;
    }

    public List<Integer> getCommits() {
        return commits;
    }

    public void setCommits(List<Integer> commits) {
        this.commits = commits;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "total=" + total +
                ", weeks=" + weeks +
                ", additions=" + additions +
                ", deletions=" + deletions +
                ", commits=" + commits +
                ", author=" + author +
                '}';
    }
}
