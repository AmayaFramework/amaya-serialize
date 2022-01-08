package io.github.amayaframework.gson;

import com.github.romanqed.jutils.structs.pipeline.Pipeline;
import io.github.amayaframework.core.AmayaBuilder;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.pipelines.Stage;

import java.util.function.Consumer;

/**
 * A class that implements a configurator that adds the necessary actions to pipelines.
 * To use it, call {@link AmayaBuilder#addConfigurator(Consumer)}
 */
public class GsonPipelineConfigurator implements Consumer<PipelineHandler> {
    @Override
    public void accept(PipelineHandler handler) {
        Pipeline input = handler.input();
        Pipeline output = handler.output();
        Controller controller = handler.getController();
        Class<?> type = null;
        Entity entity = controller.getClass().getAnnotation(Entity.class);
        if (entity != null) {
            type = entity.value();
        }
        input.insertAfter(
                Stage.PARSE_REQUEST.name(),
                GsonStage.DESERIALIZE_BODY.name(),
                new DeserializeAction(type)
        );
        output.insertAfter(
                Stage.CHECK_RESPONSE.name(),
                GsonStage.SERIALIZE_BODY.name(),
                new SerializeAction()
        );
    }
}
