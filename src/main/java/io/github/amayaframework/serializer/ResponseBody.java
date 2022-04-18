package io.github.amayaframework.serializer;

final class ResponseBody {
    private final String message;
    private final Object body;

    public ResponseBody(String message, Object body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public Object getBody() {
        return body;
    }
}
