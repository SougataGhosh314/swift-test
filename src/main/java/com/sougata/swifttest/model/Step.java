package com.sougata.swifttest.model;

import java.util.ArrayList;
import java.util.List;

public class Step {
    private String name;
    private List<?> params = new ArrayList<>();


    public String getName() {
        return name;
    }

    public List<?> getParams() {
        return params;
    }

    public Step() {
    }

    public Step(String name, List<?> params) {
        this.name = name;
        this.params = params;
    }
}
