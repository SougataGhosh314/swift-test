package com.sougata.swifttest.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sougata.swifttest.interfaces.BiConsumer;
import com.sougata.swifttest.interfaces.Consumer;
import com.sougata.swifttest.interfaces.TriConsumer;
import com.sougata.swifttest.model.Feature;
import com.sougata.swifttest.model.Scenario;
import com.sougata.swifttest.model.Step;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodStore {
    private final Map<String, Object> methodMap = new HashMap<>();
    private final TypedContext sharedContext = new TypedContext();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TypedContext getSharedContext() {
        return sharedContext;
    }

    // Register methods in bulk
    public void registerAll(Map<String, Object> methodRegistry) {
        methodRegistry.forEach((name, method) -> {
            if (method instanceof Runnable) {
                register(name, (Runnable) method);
            } else if (method instanceof Consumer) {
                register(name, (Consumer<?>) method);
            } else if (method instanceof BiConsumer) {
                register(name, (BiConsumer<?, ?>) method);
            } else if (method instanceof TriConsumer) {
                register(name, (TriConsumer<?, ?, ?>) method);
            } else {
                System.out.println("Unsupported method type for: " + name);
            }
        });
    }

    // Register methods with different parameters
    public void register(String key, Runnable method) {
        methodMap.put(key, method);
    }

    public <T> void register(String key, Consumer<T> method) {
        methodMap.put(key, method);
    }

    public <T, U> void register(String key, BiConsumer<T, U> method) {
        methodMap.put(key, method);
    }

    public <T, U, V> void register(String key, TriConsumer<T, U, V> method) {
        methodMap.put(key, method);
    }

    // Invoke methods dynamically
    public void invoke(String key, Object... args) {
        Object method = methodMap.get(key);
        if (method == null) {
            System.out.println("No method found for key: " + key);
            return;
        }

        try {
            switch (args.length) {
                case 0 -> ((Runnable) method).run();
                case 1 -> ((Consumer<Object>) method).accept(args[0]);
                case 2 -> ((BiConsumer<Object, Object>) method).accept(args[0], args[1]);
                case 3 -> ((TriConsumer<Object, Object, Object>) method).accept(args[0], args[1], args[2]);
                default -> System.out.println("Unsupported number of arguments: " + args.length);
            }
        } catch (ClassCastException e) {
            System.out.println("Parameter mismatch for method: " + key);
        }
    }

    // Read and invoke from all files in resources folder
    @SuppressWarnings("unchecked")
    public void invokeFromAllResources(Object... args) throws IOException, URISyntaxException {
        // Locate the resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("");
        if (resource == null) {
            throw new IllegalStateException("Resources folder not found!");
        }

        Path resourcesPath = Paths.get(resource.toURI());
        Files.walk(resourcesPath)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".json"))
                .forEach(path -> {
                    try (InputStream inputStream = Files.newInputStream(path)) {
                        Feature feature = objectMapper.readValue(inputStream, Feature.class);
                        System.out.println("====== Running feature: " + feature.getName() + " ======");
                        for (Scenario scenario: feature.getScenarios()) {
                            System.out.println("\t***** Running scenario: " + scenario.getName() + " *****");
                            if (!scenario.isExampled()) {
                                for (Step step : scenario.getSteps()) {
                                    invoke(step.getName(), step.getParams().toArray());
                                }
                                sharedContext.reset();
                            } else {
                                List<Map<String, Object>> examples = scenario.getExamples();

                                for (int i = 0; i < examples.size(); i++) {
                                    List<List<Object>> exampleParams = (List<List<Object>>) examples.get(i).get("params");
                                    System.out.println("\t\t----- Running example: " + (i + 1) + ", with params: " + exampleParams);

                                    int paramSet = 0;
                                    for (Step step : scenario.getSteps()) {
                                        List<Object> params = exampleParams.get(paramSet);
                                        invoke(step.getName(), params.toArray());
                                        paramSet++;
                                    }
                                    sharedContext.reset();
                                }
                            }
                            System.out.println();
                        }
                        System.out.println();
                        System.out.println();
                    } catch (IOException e) {
                        System.err.println("Failed to read file: " + path);
                        e.printStackTrace();
                    }
                });
    }
}
