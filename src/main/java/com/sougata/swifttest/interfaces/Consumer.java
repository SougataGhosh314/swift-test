package com.sougata.swifttest.interfaces;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
