package com.sougata.swifttest;

import com.sougata.swifttest.store.MethodStore;

import java.util.List;

public class BananaRunner {
    private MethodStore methodStore;
    private List<String> withTags;

    public MethodStore getMethodStore() {
        return methodStore;
    }

    public BananaRunner() {
        this.methodStore = new MethodStore();
    }

    public BananaRunner(List<String> withTags) {
        this.withTags = withTags;
        this.methodStore = new MethodStore();
    }

    public void run() {
        try {
            this.methodStore.invokeFromAllResources();
        } catch (Exception e) {
            System.out.println("Exception in running tests: " + e.getMessage());
        }
    }
}
