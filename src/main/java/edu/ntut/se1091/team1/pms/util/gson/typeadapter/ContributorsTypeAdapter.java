package edu.ntut.se1091.team1.pms.util.gson.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.Author;
import edu.ntut.se1091.team1.pms.util.gson.typeadapter.github.Contributor;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContributorsTypeAdapter extends TypeAdapter<List<Contributor>> {

    @Override
    public void write(JsonWriter writer, List<Contributor> contributors) throws IOException {
        writer.beginObject();
        writerContributorsTotal(writer, contributors);
        writer.name("all").beginArray();
        for (Contributor contributor : contributors) {
            writerContributor(writer, contributor);
        }
        writer.endArray();
        writer.endObject();
    }

    @Override
    public List<Contributor> read(JsonReader reader) throws IOException {
        List<Contributor> contributors = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            contributors.add(readContributor(reader));
        }
        reader.endArray();
        return contributors;
    }

    private void writerContributorsTotal(JsonWriter writer, List<Contributor> contributors) throws IOException {
        if (contributors.size() > 0) {
            List<String> weeks = contributors.get(0).getWeeks();
            List<Integer> additionsTotal, deletionsTotal, commitsTotal, additions, deletions, commits;
            int size = weeks.size();
            additionsTotal = new ArrayList<>(Collections.nCopies(size, 0));
            deletionsTotal = new ArrayList<>(Collections.nCopies(size, 0));
            commitsTotal = new ArrayList<>(Collections.nCopies(size, 0));
            for (Contributor contributor : contributors) {
                additions = contributor.getAdditions();
                deletions = contributor.getDeletions();
                commits = contributor.getCommits();
                for (int i = 0; i < size; i++) {
                    additionsTotal.set(i, additionsTotal.get(i) + additions.get(i));
                    deletionsTotal.set(i, deletionsTotal.get(i) + deletions.get(i));
                    commitsTotal.set(i, commitsTotal.get(i) + commits.get(i));
                }
            }
            writer.name("total").beginObject();
            writerWeeks(writer, weeks);
            writerAdditions(writer, additionsTotal);
            writerDeletions(writer, deletionsTotal);
            writerCommits(writer, commitsTotal);
            writer.endObject();
        }
    }

    private void writerContributor(JsonWriter writer, Contributor contributor) throws IOException {
        writer.beginObject();
        writer.name("total").value(contributor.getTotal());
        writerWeeks(writer, contributor.getWeeks());
        writerAdditions(writer, contributor.getAdditions());
        writerDeletions(writer, contributor.getDeletions());
        writerCommits(writer, contributor.getCommits());
        writerAuthor(writer, contributor.getAuthor());
        writer.endObject();
    }

    private Contributor readContributor(JsonReader reader) throws IOException {
        Contributor contributor = new Contributor();
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "total":
                    contributor.setTotal(reader.nextInt());
                    break;
                case "weeks":
                    readerWeeks(reader, contributor);
                    break;
                case "author":
                    contributor.setAuthor(readAuthor(reader));
                    break;
                default:
            }
        }
        reader.endObject();
        return contributor;
    }

    private void writerWeeks(JsonWriter writer, List<String> weeks) throws IOException {
        writer.name("weeks").beginArray();
        for (String week : weeks) {
            writer.value(week);
        }
        writer.endArray();
    }

    private void writerAdditions(JsonWriter writer, List<Integer> additions) throws IOException {
        writer.name("additions").beginArray();
        for (Integer week : additions) {
            writer.value(week);
        }
        writer.endArray();
    }

    private void writerDeletions(JsonWriter writer, List<Integer> deletions) throws IOException {
        writer.name("deletions").beginArray();
        for (Integer week : deletions) {
            writer.value(week);
        }
        writer.endArray();
    }

    private void writerCommits(JsonWriter writer, List<Integer> commits) throws IOException {
        writer.name("commits").beginArray();
        for (Integer week : commits) {
            writer.value(week);
        }
        writer.endArray();
    }

    private void readerWeeks(JsonReader reader, Contributor contributor) throws IOException {
        List<String> weeks = new ArrayList<>();
        List<Integer> additions = new ArrayList<>();
        List<Integer> deletions = new ArrayList<>();
        List<Integer> commits = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime localDateTime;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "w":
                        int w = reader.nextInt();
                        localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(w), ZoneId.systemDefault());
                        weeks.add(localDateTime.format(formatter));
                        break;
                    case "a":
                        additions.add(reader.nextInt());
                        break;
                    case "d":
                        deletions.add(reader.nextInt());
                        break;
                    case "c":
                        commits.add(reader.nextInt());
                        break;
                    default:
                        reader.skipValue();
                }
            }
            reader.endObject();
        }
        reader.endArray();

        contributor.setWeeks(weeks);
        contributor.setAdditions(additions);
        contributor.setDeletions(deletions);
        contributor.setCommits(commits);
    }

    private void writerAuthor(JsonWriter writer, Author author) throws IOException {
        writer.name("author").beginObject();
        writer.name("login").value(author.getLogin());
        writer.name("avatar_url").value(author.getAvatarUrl());
        writer.name("html_url").value(author.getAvatarUrl());
        writer.endObject();
    }

    private Author readAuthor(JsonReader reader) throws IOException {
        Author author = new Author();
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "login":
                    author.setLogin(reader.nextString());
                    break;
                case "avatar_url":
                    author.setAvatarUrl(reader.nextString());
                    break;
                case "html_url":
                    author.setHtmlUrl(reader.nextString());
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return author;
    }
}

