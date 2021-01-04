package edu.ntut.se1091.team1.pms.util.gson.typeadapter.github;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContributorsTotalTypeAdapter  extends ContributorsTypeAdapter {

    @Override
    public void write(JsonWriter writer, List<Contributor> contributors) throws IOException {
        Contributor contributorsTotal = contributorsTotal(contributors);
        writer.beginObject();
        writerWeeks(writer, contributorsTotal.getWeeks());
        writerAdditions(writer, contributorsTotal.getAdditions());
        writerDeletions(writer, contributorsTotal.getDeletions());
        writerCommits(writer, contributorsTotal.getCommits());
        writer.endObject();
    }

    private Contributor contributorsTotal(List<Contributor> contributors) throws IOException {
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
        return new Contributor(weeks, additionsTotal, deletionsTotal, commitsTotal);
    }
}
