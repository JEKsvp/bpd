package com.jeksvp.goalkeeper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class TestUtils {

    public static String getStringFromFile(String path) {
        try {
            return IOUtils.toString(path.getClass().getResource(path), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
