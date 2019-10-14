package com.jeksvp.goalkeeper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static String getStringFromFile(String path) {
        try {
            return IOUtils.toString(path.getClass().getResource(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
