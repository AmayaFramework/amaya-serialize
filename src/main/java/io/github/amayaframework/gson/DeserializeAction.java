package io.github.amayaframework.gson;

import com.github.romanqed.jutils.http.HttpCode;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.pipeline.InputAction;
import io.github.amayaframework.core.pipeline.RequestData;

public class DeserializeAction extends InputAction<RequestData, RequestData> {
    private static final Gson GSON = new Gson();

    private final boolean forceJson;

    public DeserializeAction(boolean forceJson) {
        this.forceJson = forceJson;
    }

    @Override
    protected void reject(HttpCode code) {
        if (forceJson) {
            interrupt(JsonResponses.responseWithCode(code, null));
        } else {
            super.reject(code);
        }
    }

    @Override
    public RequestData execute(RequestData requestData) {
        HttpRequest request = requestData.getRequest();
        if (!requestData.getMethod().isHasBody()) {
            return requestData;
        }
        if (request.getContentType() != ContentType.JSON) {
            if (forceJson) {
                reject(HttpCode.BAD_REQUEST);
            }
            return requestData;
        }
        String body = request.getBodyAsString();
        Object toSet = null;
        Class<?> type = requestData.getRoute().get(GsonConfigurator.METHOD_ENTITY);
        try {
            if (type == null) {
                toSet = JsonParser.parseString(body);
            } else {
                toSet = GSON.fromJson(body, type);
            }
        } catch (Exception e) {
            reject(HttpCode.BAD_REQUEST);
        }
        request.setBody(toSet);
        return requestData;
    }
}
