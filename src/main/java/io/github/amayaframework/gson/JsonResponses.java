package io.github.amayaframework.gson;

import com.github.romanqed.jutils.http.HttpCode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.contexts.Responses;

public class JsonResponses {
    private final static Gson GSON = new Gson();

    public static HttpResponse responseWithCode(HttpCode code, Object body) {
        HttpResponse ret = Responses.response(code);
        ret.setContentType(ContentType.JSON);
        JsonObject json = new JsonObject();
        json.addProperty("status", code.getCode());
        json.addProperty("message", code.getMessage());
        if (body != null) {
            json.add("body", GSON.toJsonTree(body));
        }
        ret.setBody(json);
        return ret;
    }

    public static HttpResponse ok(Object body) {
        return responseWithCode(HttpCode.OK, body);
    }

    public static HttpResponse ok() {
        return responseWithCode(HttpCode.OK, null);
    }

    public static HttpResponse created(Object body) {
        return responseWithCode(HttpCode.CREATED, body);
    }

    public static HttpResponse created() {
        return responseWithCode(HttpCode.CREATED, null);
    }

    public static HttpResponse accepted(Object body) {
        return responseWithCode(HttpCode.ACCEPTED, body);
    }

    public static HttpResponse accepted() {
        return responseWithCode(HttpCode.ACCEPTED, null);
    }

    public static HttpResponse badRequest(Object body) {
        return responseWithCode(HttpCode.BAD_REQUEST, body);
    }

    public static HttpResponse badRequest() {
        return responseWithCode(HttpCode.BAD_REQUEST, null);
    }

    public static HttpResponse unauthorized(Object body) {
        return responseWithCode(HttpCode.UNAUTHORIZED, body);
    }

    public static HttpResponse unauthorized() {
        return responseWithCode(HttpCode.UNAUTHORIZED, null);
    }

    public static HttpResponse forbidden(Object body) {
        return responseWithCode(HttpCode.FORBIDDEN, body);
    }

    public static HttpResponse forbidden() {
        return responseWithCode(HttpCode.FORBIDDEN, null);
    }

    public static HttpResponse notFound(Object body) {
        return responseWithCode(HttpCode.NOT_FOUND, body);
    }

    public static HttpResponse notFound() {
        return responseWithCode(HttpCode.NOT_FOUND, null);
    }

    public static HttpResponse serverError(Object body) {
        return responseWithCode(HttpCode.INTERNAL_SERVER_ERROR, body);
    }

    public static HttpResponse serverError() {
        return responseWithCode(HttpCode.INTERNAL_SERVER_ERROR, null);
    }

    public static HttpResponse badGateway(Object body) {
        return responseWithCode(HttpCode.BAD_GATEWAY, body);
    }

    public static HttpResponse badGateway() {
        return responseWithCode(HttpCode.BAD_GATEWAY, null);
    }
}
