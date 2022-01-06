package io.github.amayaframework.gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation that allows you to set an additional property
 * to the controller that stores the class of the entity that will be parsed as the request body.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    Class<?> value();
}
