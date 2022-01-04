package io.github.amayaframework.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipelines.PipelineAction;

public class SerializeAction extends PipelineAction<HttpResponse, HttpResponse> {
    private static final Gson GSON = new Gson();

    public SerializeAction() {
        super(GsonStage.SERIALIZE_BODY.name());
    }

    @Override
    public HttpResponse apply(HttpResponse response) {
        Object body = response.getBody();
        if (body instanceof JsonElement) {
            response.setBody(GSON.toJson((JsonElement) body));
        } else {
            response.setBody(GSON.toJson(response.getBody()));
        }
        return response;
    }
}
