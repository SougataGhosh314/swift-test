package com.sougata.swifttest.store;

import java.util.HashMap;
import java.util.Map;

public class TypedContext {
    private final Map<String, Object> context = new HashMap<>();

    public <T> void set(String key, T value) {
        context.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = context.get(key);
        if (value == null) {
            System.out.println("No value found for key: " + key);
            return null;
        }
        if (!type.isInstance(value)) {
            System.out.println("Type mismatch for key: " + key + ". Expected: " + type.getSimpleName());
            return null;
        }
        return type.cast(value);
    }

    public void reset() {
        context.clear();
    }
}
