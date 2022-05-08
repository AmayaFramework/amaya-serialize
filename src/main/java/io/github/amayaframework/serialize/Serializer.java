package io.github.amayaframework.serialize;

/**
 * An interface describing a universal serializer intended for subsequent use by the plugin.
 */
public interface Serializer {
    /**
     * Processes a string of a certain format and turns it into an object of the passed type.
     *
     * @param source string for processing
     * @param clazz  type of the result object
     * @param <T>    object type
     * @return an object created based on a string
     * @throws Throwable if any exception occurred during string processing or object creation
     */
    <T> T deserialize(String source, Class<T> clazz) throws Throwable;

    /**
     * Turns the passed object into its string representation.
     *
     * @param source object for processing
     * @return a string created based on an object
     * @throws Throwable if any exception occurred during object processing or string creation
     */
    String serialize(Object source) throws Throwable;
}
