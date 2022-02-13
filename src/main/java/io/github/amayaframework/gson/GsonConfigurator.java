package io.github.amayaframework.gson;

import io.github.amayaframework.core.AbstractBuilder;
import io.github.amayaframework.core.configurators.PipelineConfigurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.pipelines.InputStage;
import io.github.amayaframework.core.pipelines.OutputStage;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Collection;

/**
 * A class that implements a configurator that adds the necessary actions to pipelines.
 * To use it, call {@link AbstractBuilder#addConfigurator(PipelineConfigurator)}
 */
public class GsonConfigurator implements PipelineConfigurator {
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
    public void configure(PipelineHandler handler) {
        Controller controller = handler.getController();
        Class<?> type = null;
        Entity entity = controller.getClass().getAnnotation(Entity.class);
        Collection<MethodRoute> routes = handler.getController().getRouter().getRoutes();
        for (MethodRoute route : routes) {
            Entity routeEntity = route.getMethod().getAnnotation(Entity.class);
            if (routeEntity == null) {
                continue;
            }
            if (entity != null) {
                throw new IllegalStateException("You can't annotate a controller and a method at the same time");
            }
            route.setAttachment(METHOD_ENTITY, routeEntity.value());
        }
        if (entity != null) {
            type = entity.value();
        }
        handler.getInput().insertAfter(
                InputStage.PARSE_REQUEST_BODY,
                GsonStage.DESERIALIZE_BODY,
                new DeserializeAction(type, forceJson)
        );
        handler.getOutput().insertAfter(
                OutputStage.PROCESS_HEADERS,
                GsonStage.SERIALIZE_BODY,
                new SerializeAction()
        );
    }
}
