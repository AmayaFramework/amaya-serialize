package io.github.amayaframework.gson;

import io.github.amayaframework.core.AbstractBuilder;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.pipeline.NamedPipeline;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Collection;

/**
 * A class that implements a configurator that adds the necessary actions to pipelines.
 * To use it, call {@link AbstractBuilder#addConfigurator(Configurator)}
 */
public class GsonConfigurator implements Configurator {
    public static final String METHOD_ENTITY = "mt";
    private final boolean forceJson;

    /**
     * Create configurator for gson plugin. The parameter "forceJson" is responsible for the behavior of pipelines
     * during request processing.
     *
     * @param forceJson If true, it will reject all requests whose body is not a json object.
     */
    public GsonConfigurator(boolean forceJson) {
        this.forceJson = forceJson;
    }

    public GsonConfigurator() {
        this(true);
    }

    @Override
    public void configureController(Controller controller) {
        Class<?> type = null;
        Entity entity = controller.getClass().getAnnotation(Entity.class);
        if (entity != null) {
            type = entity.value();
        }
        Collection<MethodRoute> routes = controller.getRoutes();
        for (MethodRoute route : routes) {
            if (type != null) {
                route.setAttachment(METHOD_ENTITY, type);
            } else {
                Entity routeEntity = route.getMethod().getAnnotation(Entity.class);
                if (routeEntity != null) {
                    route.setAttachment(METHOD_ENTITY, routeEntity.value());
                }
            }
        }
    }

    @Override
    public void configureInput(NamedPipeline input) {
        input.put(new DeserializeAction(forceJson));
    }

    @Override
    public void configureOutput(NamedPipeline output) {
        output.put(new SerializeAction());
    }
}
