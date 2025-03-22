package com.sougata.swifttest;


import com.sougata.swifttest.interfaces.BiConsumer;
import com.sougata.swifttest.interfaces.Consumer;
import com.sougata.swifttest.interfaces.TriConsumer;
import com.sougata.swifttest.store.MethodStore;
import com.sougata.swifttest.store.TypedContext;

import java.util.Map;
import java.util.Objects;

public class SampleUsage {
    private static final com.sougata.swifttest.BananaRunner bananaRunner = new BananaRunner();

    public static void main(String[] args) {
        MethodStore store = bananaRunner.getMethodStore();
        TypedContext context = store.getSharedContext();

        // Method registration map
        Map<String, Object> methodRegistry = Map.of(
                "noArgMethod", (Runnable) () -> System.out.println("No argument method executed."),
                "oneArgMethod", (Consumer<String>) (s) -> System.out.println("One arg: " + s),
                "twoArgMethod", (BiConsumer<String, Integer>) (a, b) -> System.out.println("Two args: " + a + ", " + b),
                "threeArgMethod", (TriConsumer<String, Integer, Boolean>) (a, b, c) -> System.out.println("Three args: " + a + ", " + b + ", " + c),
                "I have two numbers", (BiConsumer<Integer, Integer>) (a, b) -> {
                    context.set("number1", a);
                    context.set("number2", b);
                },
                "I add them", (Runnable) () -> {
                    context.set("result", context.get("number1", Integer.class) + context.get("number2", Integer.class));
                },
                "I multiply them", (Runnable) () -> {
                    context.set("result", context.get("number1", Integer.class) * context.get("number2", Integer.class));
                },
                "The result should be", (Consumer<Integer>) (s) -> {
                    if (Objects.equals(s, context.get("result", Integer.class)))
                        System.out.println("It can operate!");
                    else System.out.println("Scenario failed");
                }
        );

        store.registerAll(methodRegistry);
        bananaRunner.run();
    }
}
