package io.github.amayaframework.serializer;

import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.contexts.Responses;

final class Constants {
    static final String METHOD_ENTITY = "METHOD_ENTITY";
    static final HttpResponse ERROR_RESPONSE = makeErrorResponse();

    private static HttpResponse makeErrorResponse() {
        HttpResponse ret = Responses.serverError();
        ret.setContentType(ContentType.PLAIN);
        ret.setBody("Entity serialization error");
        return ret;
    }
}
