package io.github.amayaframework.gson;

import com.github.romanqed.jutils.structs.Pair;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.pipelines.PipelineAction;
import io.github.amayaframework.core.routers.Route;

public class DeserializeAction extends PipelineAction<Pair<HttpRequest, Route>, Pair<HttpRequest, Route>> {
    private static final Gson GSON = new Gson();
    private final Class<?> type;

    public DeserializeAction(Class<?> type) {
        super(GsonStage.DESERIALIZE_BODY.name());
        this.type = type;
    }

    @Override
    public Pair<HttpRequest, Route> apply(Pair<HttpRequest, Route> pair) {
        HttpRequest request = pair.getKey();
        String body = (String) request.getBody();
        if (type == null) {
            request.setBody(JsonParser.parseString(body));
        } else {
            request.setBody(GSON.fromJson(body, type));
        }
        return pair;
    }
}
