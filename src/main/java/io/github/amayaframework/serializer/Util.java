package io.github.amayaframework.serializer;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.contexts.Responses;
import io.github.amayaframework.core.util.ReflectUtil;
import io.github.amayaframework.http.ContentType;

import java.util.Collections;
import java.util.Map;

final class Util {
    static final Map<ContentType, Serializer> SERIALIZERS = getSerializers();
    static final String METHOD_ENTITY = "METHOD_ENTITY";
    static final HttpResponse ERROR_RESPONSE = makeErrorResponse();

    private static HttpResponse makeErrorResponse() {
        HttpResponse ret = Responses.serverError();
        ret.setContentType(ContentType.PLAIN);
        ret.setBody("Entity serialization error");
        return ret;
    }

    private static Map<ContentType, Serializer> getSerializers() {
        Map<ContentType, Serializer> ret;
        try {
            ret = ReflectUtil.findAnnotatedWithValue(TypedSerializer.class, Serializer.class);
        } catch (Throwable e) {
            throw new IllegalStateException("Unable to find serializers due to", e);
        }
        return Collections.unmodifiableMap(ret);
    }
}
