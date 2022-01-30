package io.github.amayaframework.gson;

import com.github.romanqed.jutils.structs.pipeline.Pipeline;
import io.github.amayaframework.core.AbstractBuilder;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.handlers.IOHandler;
import io.github.amayaframework.core.pipelines.Stage;

/**
 * A class that implements a configurator that adds the necessary actions to pipelines.
 * To use it, call {@link AbstractBuilder#addConfigurator(Configurator)}
 */
public class GsonConfigurator implements Configurator {
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
    public void accept(IOHandler handler) {
        Pipeline input = handler.getInput();
        Pipeline output = handler.getOutput();
        Controller controller = handler.getController();
        Class<?> type = null;
        Entity entity = controller.getClass().getAnnotation(Entity.class);
        if (entity != null) {
            type = entity.value();
        }
        input.insertAfter(
                Stage.PARSE_REQUEST_BODY.name(),
                GsonStage.DESERIALIZE_BODY.name(),
                new DeserializeAction(type, forceJson)
        );
        output.insertAfter(
                Stage.CHECK_RESPONSE.name(),
                GsonStage.SERIALIZE_BODY.name(),
                new SerializeAction(forceJson)
        );
    }
}
