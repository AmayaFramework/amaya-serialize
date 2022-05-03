package io.github.amayaframework.serializer;

import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.pipeline.NamedPipeline;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Collection;

/**
 * The configurator that needs to be added to the builder if you need to use the functionality of the plugin.
 * <p>Added using {@link io.github.amayaframework.core.AmayaBuilder#addConfigurator(Configurator)}</p>
 */
public final class SerializeConfigurator implements Configurator {

    @Override
    public void configureController(Controller controller) {
        Entity entity = controller.getControllerClass().getAnnotation(Entity.class);
        Collection<MethodRoute> routes = controller.getRoutes();
        if (entity != null) {
            routes.forEach(route -> route.set(Util.METHOD_ENTITY, entity.value()));
            return;
        }
        for (MethodRoute route : routes) {
            Entity routeEntity = route.getMethod().getAnnotation(Entity.class);
            if (routeEntity != null) {
                route.set(Util.METHOD_ENTITY, routeEntity.value());
            }
        }
    }

    @Override
    public void configureInput(NamedPipeline input) {
        input.put(new DeserializeAction());
    }

    @Override
    public void configureOutput(NamedPipeline output) {
        output.put(new SerializeAction());
    }
}
