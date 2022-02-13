package io.github.amayaframework.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.github.amayaframework.core.contexts.ContentType;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipelines.PipelineAction;
import io.github.amayaframework.core.pipelines.ResponseData;

public class SerializeAction extends PipelineAction<ResponseData, ResponseData> {
    private static final Gson GSON = new Gson();

    @Override
    public ResponseData execute(ResponseData responseData) {
        HttpResponse response = responseData.getResponse();
        if (response.getContentType() != ContentType.JSON) {
            return responseData;
        }
        Object body = response.getBody();
        if (body instanceof JsonElement) {
            response.setBody(body.toString());
        } else {
            response.setBody(GSON.toJson(response.getBody()));
        }
        return responseData;
    }
}
