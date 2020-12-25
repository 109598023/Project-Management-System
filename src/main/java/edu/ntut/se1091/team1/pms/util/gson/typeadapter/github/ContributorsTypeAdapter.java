package edu.ntut.se1091.team1.pms.util.gson.typeadapter.github;

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
        writer.beginArray();
        for (Contributor contributor : contributors) {
            writerContributor(writer, contributor);
        }
        writer.endArray();
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

    protected void writerContributor(JsonWriter writer, Contributor contributor) throws IOException {
        writer.beginObject();
        writer.name("total").value(contributor.getTotal());
        writerWeeks(writer, contributor.getWeeks());
        writerAdditions(writer, contributor.getAdditions());
        writerDeletions(writer, contributor.getDeletions());
        writerCommits(writer, contributor.getCommits());
        writerAuthor(writer, contributor.getAuthor());
        writer.endObject();
    }

    protected Contributor readContributor(JsonReader reader) throws IOException {
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

    protected void writerWeeks(JsonWriter writer, List<String> weeks) throws IOException {
        writer.name("weeks").beginArray();
        for (String week : weeks) {
            writer.value(week);
        }
        writer.endArray();
    }

    protected void writerAdditions(JsonWriter writer, List<Integer> additions) throws IOException {
        writer.name("additions").beginArray();
        for (Integer week : additions) {
            writer.value(week);
        }
        writer.endArray();
    }

    protected void writerDeletions(JsonWriter writer, List<Integer> deletions) throws IOException {
        writer.name("deletions").beginArray();
        for (Integer week : deletions) {
            writer.value(week);
        }
        writer.endArray();
    }

    protected void writerCommits(JsonWriter writer, List<Integer> commits) throws IOException {
        writer.name("commits").beginArray();
        for (Integer week : commits) {
            writer.value(week);
        }
        writer.endArray();
    }

    protected void readerWeeks(JsonReader reader, Contributor contributor) throws IOException {
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

    protected void writerAuthor(JsonWriter writer, Author author) throws IOException {
        writer.name("author").beginObject();
        writer.name("login").value(author.getLogin());
        writer.name("avatar_url").value(author.getAvatarUrl());
        writer.name("html_url").value(author.getAvatarUrl());
        writer.endObject();
    }

    protected Author readAuthor(JsonReader reader) throws IOException {
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

