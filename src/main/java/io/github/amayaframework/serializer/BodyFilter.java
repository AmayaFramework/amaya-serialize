package io.github.amayaframework.serializer;

import io.github.amayaframework.core.wrapping.Content;
import io.github.amayaframework.filters.ContentFilter;
import io.github.amayaframework.filters.NamedFilter;

@NamedFilter(Content.BODY)
public class BodyFilter implements ContentFilter {
    @Override
    public Object transform(Object source, String name) {
        return source;
    }
}
