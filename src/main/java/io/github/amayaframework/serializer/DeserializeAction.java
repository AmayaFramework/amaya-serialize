package io.github.amayaframework.serializer;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.pipeline.InputAction;
import io.github.amayaframework.core.pipeline.RequestData;
import io.github.amayaframework.http.HttpCode;

final class DeserializeAction extends InputAction<RequestData, RequestData> {

    @Override
    public RequestData execute(RequestData data) {
        if (!data.getMethod().isHasBody()) {
            return data;
        }
        Class<?> type = data.getRoute().getCast(Util.METHOD_ENTITY);
        if (type == null) {
            return data;
        }
        HttpRequest request = data.getRequest();
        Serializer serializer = Util.SERIALIZERS.get(request.getContentType());
        if (serializer == null) {
            reject(HttpCode.BAD_REQUEST);
            // Return something to suppress npe warning
            return data;
        }
        String body = request.getBodyAsString();
        try {
            Object toSet = serializer.deserialize(body, type);
            request.setBody(toSet);
        } catch (Throwable e) {
            reject(HttpCode.BAD_REQUEST);
        }
        return data;
    }
}
