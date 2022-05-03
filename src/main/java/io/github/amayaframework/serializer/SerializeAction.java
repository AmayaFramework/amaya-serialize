package io.github.amayaframework.serializer;

import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipeline.PipelineAction;
import io.github.amayaframework.core.pipeline.ResponseData;

final class SerializeAction extends PipelineAction<ResponseData, ResponseData> {
    @Override
    public ResponseData execute(ResponseData data) {
        if (data.isCompleted()) {
            return data;
        }
        HttpResponse response = data.getResponse();
        Serializer serializer = Util.SERIALIZERS.get(response.getContentType());
        if (serializer == null) {
            return data;
        }
        try {
            String toSet = serializer.serialize(response.getBody());
            response.setBody(toSet);
        } catch (Throwable e) {
            data.setResponse(Util.ERROR_RESPONSE);
        }
        return data;
    }
}
