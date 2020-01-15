package uk.co.evoco.webdriver.configuration.utils;

import java.io.File;
import java.io.IOException;

public final class FileLoader {

    /**
     * Look for a file to load on a fully qualified path, or the classpath
     * @param path
     * @return File
     * @throws IOException
     */
    public static File loadFromClasspathOrFileSystem(String path) throws IOException {
        // Check if file is available at the given path
        if(new File(path).exists()) {
            return new File(path);
        }

        // check if its on the classpath
        if(new File(ClassLoader.getSystemResource(path).getFile()).exists()) {
            return new File(ClassLoader.getSystemResource(path).getFile());
        }
        throw new IOException("No file found at given path: " + path);
    }
}
