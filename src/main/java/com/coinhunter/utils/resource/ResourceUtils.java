package com.coinhunter.utils.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ResourceUtils {

    public static InputStream getInputStreamByFilePath(String filePath) throws IOException {
        Objects.requireNonNull(filePath);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("Resource file Could not find");
        }
        return inputStream;
    }
}
