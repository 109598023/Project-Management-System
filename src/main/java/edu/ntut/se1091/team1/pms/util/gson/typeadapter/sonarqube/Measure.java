package edu.ntut.se1091.team1.pms.util.gson.typeadapter.sonarqube;

public class Measure {

    private String metric;

    private String value;

    public Measure(String metric, String value) {
        this.metric = metric;
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "metric='" + metric + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
