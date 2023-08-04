package com.snw.rest.mapper;

import com.snw.annotation.MessageBundle;
import com.snw.util.Bundle;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    @MessageBundle
    Bundle messageBundle;

    @Override
    public Response toResponse(WebApplicationException exception) {
        String message = null;
        if (!"".equals(exception.getMessage())) {
            message = exception.getMessage();
        } else {
            message = messageBundle.getProperty("record.filterIsEmpty");
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .header("X-Internal-Server-Error", message)
                .entity(exception.getMessage())
                .build();
    }
}
