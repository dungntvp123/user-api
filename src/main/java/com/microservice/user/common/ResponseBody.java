package com.microservice.user.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "construct")
@NoArgsConstructor
@Builder
public class ResponseBody<T> {
    private long code;
    private String message;
    private String status;
    private T body;

    public ResponseBody(ResponseStatus status, T body) {
        this.body = body;
        this.code = status.code;
        this.message = status.message;
        this.status = status.status;
    }

    public ResponseBody(ResponseStatus status) {
        this.body = (T) "#";
        this.code = status.code;
        this.message = status.message;
        this.status = status.status;
    }
}
