package io.github.amayaframework.gson;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.pipelines.AbstractRequestData;
import io.github.amayaframework.core.pipelines.PipelineAction;
import io.github.amayaframework.server.utils.HttpCode;

import java.util.*;

public class DeserializeAction extends PipelineAction<AbstractRequestData, AbstractRequestData> {
    private static final Gson GSON = new Gson();
    private static final Set<HttpMethod> NO_BODY;

    static {
        List<HttpMethod> methods = Arrays.asList(HttpMethod.GET, HttpMethod.HEAD);
        NO_BODY = Collections.unmodifiableSet(new HashSet<>(methods));
    }

    private final Class<?> type;

    public DeserializeAction(Class<?> type) {
        this.type = type;
    }

    @Override
    public AbstractRequestData apply(AbstractRequestData requestData) {
        HttpRequest request = requestData.getRequest();
        if (NO_BODY.contains(request.getMethod())) {
            return requestData;
        }
        String body = (String) request.getBody();
        if (type == null) {
            request.setBody(JsonParser.parseString(body));
        } else {
            try {
                request.setBody(GSON.fromJson(body, type));
            } catch (Exception e) {
                reject(HttpCode.BAD_REQUEST);
            }
        }
        return requestData;
    }
}
