package uk.co.evoco.webdriver.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple class that helps serialize and deserialize JSON objects in various formats and inputs
 */
public final class JsonUtils {

    /**
     * Deserialize a JSON from a String to a given class type
     * @param jsonString
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fromString(String jsonString, TypeReference<T> type) throws IOException {
        return new ObjectMapper().readValue(jsonString, type);
    }

    /**
     * Deserialize a JSON from a String to a given class type
     * @param jsonString
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fromString(String jsonString, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(jsonString, type);
    }

    /**
     * Deserialize a JSON from a File to a given class type
     * @param file
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fromFile(File file, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(file, type);
    }

    /**
     * Deserialize a JSON from a File to a given class type
     * @param jsonStream
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fromFile(InputStream jsonStream, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(jsonStream, type);
    }

    /**
     * Deserialize a JSON from a File to a given class type
     * @param filePath
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fromFile(String filePath, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(new File(filePath), type);
    }

    /**
     * Serializes a given object instance into a JSON and returns it as a String
     * @param object
     * @return String
     * @throws IOException
     */
    public static String toString(Object object) throws IOException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
