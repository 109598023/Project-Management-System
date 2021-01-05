package edu.ntut.se1091.team1.pms.util.gson.typeadapter.sonarqube;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeasuresTypeAdapter extends TypeAdapter<List<Measure>> {

    @Override
    public void write(JsonWriter writer, List<Measure> measures) throws IOException {
        writer.beginObject();
        for (Measure measure : measures) {
            writer.name(measure.getMetric()).beginObject();
            writer.name("value").value(measure.getValue());
            writer.endObject();
        }
        writer.endObject();
    }

    @Override
    public List<Measure> read(JsonReader reader) throws IOException {
        List<Measure> measures = List.of();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("component")) {
                reader.beginObject();
                while(reader.hasNext()) {
                    if (reader.nextName().equals("measures")) {
                        measures = readMeasures(reader);
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return measures;
    }

    private List<Measure> readMeasures(JsonReader reader) throws IOException {
        List<Measure> measures = new ArrayList<>();
        String metric ="";
        String value = "";
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "metric":
                        metric = reader.nextString();
                        break;
                    case "value":
                        value = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                }
            }
            reader.endObject();
            measures.add(new Measure(metric, value));
        }
        reader.endArray();
        return measures;
    }
}
