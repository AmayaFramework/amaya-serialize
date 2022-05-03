package io.github.amayaframework.serializer;

import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.pipeline.NamedPipeline;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The configurator that needs to be added to the builder if you need to use the functionality of the plugin.
 * <p>Added using {@link io.github.amayaframework.core.AmayaBuilder#addConfigurator(Configurator)}</p>
 */
public final class SerializeConfigurator implements Configurator {
    private final AtomicBoolean flag = new AtomicBoolean(false);

    @Override
    public void configureController(Controller controller) {
        flag.set(false);
        Entity entity = controller.getControllerClass().getAnnotation(Entity.class);
        Collection<MethodRoute> routes = controller.getRoutes();
        if (entity != null) {
            flag.set(true);
            routes.forEach(route -> route.set(Util.METHOD_ENTITY, entity.value()));
            return;
        }
        for (MethodRoute route : routes) {
            Entity routeEntity = route.getMethod().getAnnotation(Entity.class);
            if (routeEntity != null) {
                flag.set(true);
                route.set(Util.METHOD_ENTITY, routeEntity.value());
            }
        }
    }

    @Override
    public void configureInput(NamedPipeline input) {
        if (flag.get()) {
            input.put(new DeserializeAction());
        }
    }

    @Override
    public void configureOutput(NamedPipeline output) {
        if (flag.get()) {
            output.put(new SerializeAction());
        }
    }
}
