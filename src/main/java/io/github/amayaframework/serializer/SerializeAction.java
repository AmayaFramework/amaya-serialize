package io.github.amayaframework.serializer;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipeline.PipelineAction;
import io.github.amayaframework.core.pipeline.ResponseData;

final class SerializeAction extends PipelineAction<ResponseData, ResponseData> {
    private final Serializer serializer;

    SerializeAction(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public ResponseData execute(ResponseData data) {
        if (data.isCompleted()) {
            return data;
        }
        HttpResponse response = data.getResponse();
        if (!response.getContentType().isString()) {
            return data;
        }
        ResponseBody body = new ResponseBody(response.getCode().getMessage(), response.getBody());
        try {
            String toSet = serializer.serialize(body);
            response.setContentType(serializer.getContentType());
            response.setBody(toSet);
        } catch (Exception e) {
            data.setResponse(Constants.ERROR_RESPONSE);
        }
        return data;
    }
}
