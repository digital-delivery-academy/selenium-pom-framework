package uk.co.evoco.webdriver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple class that helps serialize and deserialize JSON objects in various formats and inputs
 */
public final class JsonUtils {

    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Deserialize a JSON from a String to a given class type
     * @param jsonString json string
     * @param type target class type or collection
     * @param <T> generic type
     * @return generic class for mapper
     * @throws JsonProcessingException if JSON cannot be processed
     */
    public static <T> T fromString(String jsonString, TypeReference<T> type) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonString, type);
    }

    /**
     * Deserialize a JSON from a String to a given class type
     * @param jsonString json string
     * @param type target class type or collection
     * @param <T> generic type
     * @return generic class for mapper
     * @throws JsonProcessingException if JSON cannot be processed
     */
    public static <T> T fromString(String jsonString, Class<T> type) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonString, type);
    }

    /**
     * Deserialize a JSON from a File to a given class type
     * @param file json file
     * @param type target class type or collection
     * @param <T> generic type
     * @return generic class for mapper
     * @throws IOException if file cannot be found
     */
    public static <T> T fromFile(File file, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(file, type);
    }

    /**
     *
     * @param file json file
     * @param type target class type of collection
     * @param <T> generic type
     * @return generic class for mapper
     * @throws IOException if file cannot be found
     */
    public static <T> T fromFile(File file, TypeReference<T> type) throws IOException {
        return new ObjectMapper().readValue(file, type);
    }

    /**
     * Deserialize a JSON from a File to a given class type
     * @param jsonStream json stream
     * @param type target class type or collection
     * @param <T> generic type
     * @return generic class for mapper
     * @throws IOException if file cannot be found
     */
    public static <T> T fromFile(InputStream jsonStream, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(jsonStream, type);
    }

    /**
     * Deserialize a JSON from a File to a given class type
     * @param filePath json file path
     * @param type target class type or collection
     * @param <T> generic type
     * @return generic class for mapper
     * @throws IOException if file cannot be found
     */
    public static <T> T fromFile(String filePath, Class<T> type) throws IOException {
        return new ObjectMapper().readValue(new File(filePath), type);
    }

    /**
     * Serializes a given object instance into a JSON and returns it as a String
     * @param object json object
     * @return String that represents JSON
     * @throws JsonProcessingException if JSON cannot be parsed
     */
    public static String toString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
