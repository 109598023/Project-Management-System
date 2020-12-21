package edu.ntut.se1091.team1.pms.dto.response;

import org.springframework.http.HttpStatus;

public class Response<T> {

    private final HttpStatus status;
    private final Integer statusCode;
    private final String message;
    private final T data;

    public Response() {
        this(null);
    }

    public Response(T data) {
        this(HttpStatus.OK, "success", data);
    }

    public Response(HttpStatus status, String message, T data) {
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
