package io.github.amayaframework.gson;

import com.github.romanqed.jutils.http.HttpCode;
import io.github.amayaframework.core.pipelines.PipelineAction;

public abstract class JsonAction<T, R> extends PipelineAction<T, R> {
    protected final boolean forceJson;

    protected JsonAction(boolean forceJson) {
        this.forceJson = forceJson;
    }

    @Override
    protected void reject(HttpCode code) {
        if (forceJson) {
            interrupt(JsonResponses.responseWithCode(code, null));
        } else {
            super.reject(code);
        }
    }
}
