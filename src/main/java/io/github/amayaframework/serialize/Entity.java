package io.github.amayaframework.serialize;

import java.lang.annotation.*;

/**
 * An annotation indicating that the controller or its method expects an instance of the specified class as the body.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Entity {
    Class<?> value();
}
