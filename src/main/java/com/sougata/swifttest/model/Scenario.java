package com.sougata.swifttest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scenario {
    private String name;
    private List<String> tags;
    private boolean exampled;
    private List<Step> steps;
    private List<Map<String, Object>> examples = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public boolean isExampled() {
        return exampled;
    }

    public List<Map<String, Object>> getExamples() {
        return examples;
    }

    public Scenario() {
    }

    public Scenario(String name, List<String> tags, List<Step> steps, boolean exampled, List<Map<String, Object>> examples) {
        this.name = name;
        this.tags = tags;
        this.steps = steps;
        this.exampled = exampled;
        this.examples = examples;
    }
}
