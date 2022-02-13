package io.github.amayaframework.gson;

import com.github.romanqed.jutils.http.HttpCode;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.pipelines.InputAction;
import io.github.amayaframework.core.pipelines.RequestData;

import java.util.*;

public class DeserializeAction extends InputAction<RequestData, RequestData> {
    private static final Gson GSON = new Gson();
    private static final Set<HttpMethod> NO_BODY;

    static {
        List<HttpMethod> methods = Arrays.asList(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.OPTIONS);
        NO_BODY = Collections.unmodifiableSet(new HashSet<>(methods));
    }

    private final boolean forceJson;
    private final Class<?> type;

    public DeserializeAction(Class<?> type, boolean forceJson) {
        this.forceJson = forceJson;
        this.type = type;
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
        if (NO_BODY.contains(request.getMethod())) {
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
        Class<?> type = this.type;
        if (type == null) {
            type = requestData.getRoute().get(GsonConfigurator.METHOD_ENTITY);
        }
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
