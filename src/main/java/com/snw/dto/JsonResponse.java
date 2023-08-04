package com.snw.dto;

import lombok.Data;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import java.io.Serializable;

@Data
public class JsonResponse<T> implements Serializable {

    private int status;

    private Family family;

    private String reason;

    private String traceId;

    private T data;


    /**
     * Used for response mapping.
     */
    public JsonResponse() {
    }

    public JsonResponse(Status status) {
        super();
        this.status = status.getStatusCode();
        this.family = status.getFamily();
        this.reason = status.getReasonPhrase();
    }

    public JsonResponse(Status status, T data) {
        this(status);
        this.data = data;
    }
}
