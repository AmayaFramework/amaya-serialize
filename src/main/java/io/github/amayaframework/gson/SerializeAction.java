package io.github.amayaframework.gson;

import com.github.romanqed.jutils.http.HttpCode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpResponse;

public class SerializeAction extends JsonAction<HttpResponse, HttpResponse> {
    private static final Gson GSON = new Gson();

    public SerializeAction(boolean forceJson) {
        super(forceJson);
    }

    @Override
    public HttpResponse execute(HttpResponse response) {
        if (response.getContentType() != ContentType.JSON) {
            if (forceJson) {
                reject(HttpCode.INTERNAL_SERVER_ERROR);
            }
            return response;
        }
        Object body = response.getBody();
        if (body instanceof JsonElement) {
            response.setBody(body.toString());
        } else {
            response.setBody(GSON.toJson(response.getBody()));
        }
        return response;
    }
}
