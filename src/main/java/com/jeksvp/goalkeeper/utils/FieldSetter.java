package com.jeksvp.goalkeeper.utils;

import java.util.function.Consumer;

public class FieldSetter {

    public static <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
