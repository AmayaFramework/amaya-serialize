package io.github.amayaframework.serializer;

import io.github.amayaframework.core.configurators.Access;
import io.github.amayaframework.core.configurators.AccessPolicy;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.pipeline.NamedPipeline;
import io.github.amayaframework.core.routes.MethodRoute;

import java.util.Collection;
import java.util.Objects;

public final class SerializeConfigurator implements Configurator {
    private final Serializer serializer;

    public SerializeConfigurator(Serializer serializer) {
        this.serializer = Objects.requireNonNull(serializer);
    }

    @Override
    public void configureController(Controller controller) {
        Entity entity = controller.getClass().getAnnotation(Entity.class);
        Collection<MethodRoute> routes = controller.getRoutes();
        if (entity != null) {
            routes.forEach(route -> route.setAttachment(Constants.METHOD_ENTITY, entity.value()));
            return;
        }
        for (MethodRoute route : routes) {
            Entity routeEntity = route.getMethod().getAnnotation(Entity.class);
            if (routeEntity != null) {
                route.setAttachment(Constants.METHOD_ENTITY, routeEntity.value());
            }
        }
    }

    @Override
    public void configureInput(NamedPipeline input) {
        input.put(new DeserializeAction(serializer));
    }

    @Override
    @Access(AccessPolicy.DIRECT)
    public void configureOutput(NamedPipeline output) {
        output.insertFirst(new SerializeAction(serializer));
    }
}
