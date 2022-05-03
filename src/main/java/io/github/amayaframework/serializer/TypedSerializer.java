package io.github.amayaframework.serializer;

import io.github.amayaframework.http.ContentType;
import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

/**
 * Annotation for automatically adding an {@link Serializer} to the plugin collection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@IndexAnnotated
@Documented
public @interface TypedSerializer {
    ContentType value();
}
