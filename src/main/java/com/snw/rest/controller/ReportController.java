/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snw.rest.controller;

import com.snw.entity.ReportEntity;
import com.snw.entity.ReportEntity;
import com.snw.service.ReportCommandService;
import com.snw.service.ReportQueryService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ReportController {

    @Inject
    ReportQueryService queryService;

    @Inject
    ReportCommandService commandService;

    @GET
    @Path("/v1/search")
    public List<ReportEntity> search(@QueryParam("start") Integer start,
                                         @QueryParam("limit") Integer limit,
                                         @Context UriInfo ui,
                                         @Context HttpServletRequest request,
                                         @Context SecurityContext sc) {
        return queryService.search(start, limit, null, null);
    }

    @POST
    @Path("/v1/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public ReportEntity createCar(@Valid @NotNull ReportEntity reportEntity) {
        return commandService.create(reportEntity);
    }
}
