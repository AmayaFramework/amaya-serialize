package io.github.amayaframework.serializer;

import com.github.romanqed.jutils.http.HttpCode;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.pipeline.InputAction;
import io.github.amayaframework.core.pipeline.RequestData;

final class DeserializeAction extends InputAction<RequestData, RequestData> {
    private final Serializer serializer;

    DeserializeAction(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public RequestData execute(RequestData data) {
        if (data.isCompleted()) {
            return data;
        }
        if (!data.getMethod().isHasBody()) {
            return data;
        }
        Class<?> type = data.getRoute().get(Constants.METHOD_ENTITY);
        if (type == null) {
            return data;
        }
        HttpRequest request = data.getRequest();
        if (request.getContentType() != serializer.getContentType()) {
            reject(HttpCode.BAD_REQUEST);
        }
        String body = request.getBodyAsString();
        try {
            Object toSet = serializer.deserialize(body, type);
            request.setBody(toSet);
        } catch (Exception e) {
            reject(HttpCode.BAD_REQUEST);
        }
        return data;
    }
}
