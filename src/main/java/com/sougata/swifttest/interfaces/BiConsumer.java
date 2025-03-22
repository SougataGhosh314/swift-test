package com.sougata.swifttest.interfaces;

@FunctionalInterface
public interface BiConsumer<T, U> {
    void accept(T t, U u);
}
