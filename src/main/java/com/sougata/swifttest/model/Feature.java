package com.sougata.swifttest.model;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    private String name;
    private List<String> tags = new ArrayList<>();
    private List<Scenario> scenarios = new ArrayList<>();

    public Feature() {
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public Feature(String name, List<String> tags, List<Scenario> scenarios) {
        this.name = name;
        this.tags = tags;
        this.scenarios = scenarios;
    }
}
