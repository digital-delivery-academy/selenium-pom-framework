package uk.co.evoco.webdriver.configuration.utils;

import java.io.File;
import java.io.IOException;

public final class FileLoaderUtils {

    private FileLoaderUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Look for a file to load on a fully qualified path, or the classpath
     * @param path location to look for file (either relative or classpath)
     * @return File the file that represents the found file on either the path or the classpath
     * @throws IOException if we can't find the file on the path or the classpath
     */
    public static File loadFromClasspathOrFileSystem(String path) throws IOException {
        File file = new File(path);
        if (file.isFile()) {
            return file;
        } else {
            try {
                return new File(ClassLoader.getSystemResource(path).getPath());
            } catch(NullPointerException e) {
                throw new IOException("File could not be found: " + path);
            }
        }
    }
}
