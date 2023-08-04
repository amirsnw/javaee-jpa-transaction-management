package com.snw.rest.util;

import com.snw.dto.JsonResponse;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;

public class ResponseHelper {

    public static Response ok() {
        return Response.status(Status.OK).entity(new JsonResponse(Status.OK))
                .build();
    }

    public static Response ok(Object data) {
        return Response.status(Status.OK)
                .entity(new JsonResponse(Status.OK, data)).build();
    }

    public static Response serverError() {
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    public static Response serverError(Object data) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(data)
                .build();
    }

    public static Response notFound() {
        return Response.status(Status.NOT_FOUND)
                .entity(new JsonResponse(Status.NOT_FOUND)).build();
    }

    public static Response conflict() {
        return Response.status(Status.CONFLICT)
                .entity(new JsonResponse(Status.CONFLICT)).build();
    }

    public static Response created(URI location) {
        return Response.created(location)
                .entity(new JsonResponse(Status.CREATED)).build();
    }

    // no body ---------------------------------------------->
    public static Response forbidden() {
        return Response.status(Status.FORBIDDEN).build();
    }

    public static Response noContent() {
        return Response.status(Status.NO_CONTENT).build();
    }

    public static Response notModified() {
        return Response.status(Status.NOT_MODIFIED).build();
    }

    public static Response notModified(EntityTag tag) {
        return Response.status(Status.NOT_MODIFIED).tag(tag).build();
    }

    public static Response seeOther(URI location) {
        return Response.status(Status.SEE_OTHER).location(location).build();
    }

    public static Response temporaryRedirect(URI location) {
        return Response.status(Status.TEMPORARY_REDIRECT).location(location)
                .build();
    }
    // <---------------------------------------------------------

    public static JsonResponse wrapResponse(Response response) {
        return new JsonResponse(Status.fromStatusCode(response.getStatus()),
                response.getEntity());
    }
}
