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
