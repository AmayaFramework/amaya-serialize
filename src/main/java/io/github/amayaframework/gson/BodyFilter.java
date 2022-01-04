package io.github.amayaframework.gson;

import com.google.gson.JsonElement;
import io.github.amayaframework.filters.ContentFilter;
import io.github.amayaframework.filters.NamedFilter;

@NamedFilter("body")
public class BodyFilter implements ContentFilter {
    @Override
    public Object transform(Object source, String name) {
        if (source instanceof JsonElement) {
            JsonElement element = (JsonElement) source;
            if (!name.isEmpty() && element.isJsonObject()) {
                return element.getAsJsonObject().get(name);
            }
            return element;
        } else {
            return source;
        }
    }
}
