package io.github.amayaframework.serializer;

import io.github.amayaframework.core.contexts.ContentType;

public interface Serializer {
    <T> T deserialize(String source, Class<T> clazz) throws Exception;

    String serialize(Object source) throws Exception;

    ContentType getContentType();
}
