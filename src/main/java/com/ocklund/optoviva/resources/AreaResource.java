package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.api.Result;
import com.ocklund.optoviva.db.Storage;
import lombok.extern.java.Log;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Log
@Path("/area")
public class AreaResource {

    private final Storage storage;

    public AreaResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    @Timed
    public Area getArea(@PathParam("id") String id) {
        log.info(String.format("id: %s", id));
        Optional<Area> area = storage.getArea(id);
        if (area.isPresent()) {
            return area.get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @GET
    @Path("/search")
    @Produces(APPLICATION_JSON)
    @Timed
    public Area searchArea(@QueryParam("locationId") @NotNull String locationId,
                           @QueryParam("categoryId") @NotNull String categoryId,
                           @QueryParam("score") @NotNull Integer score) {
        log.info(String.format("locationId: %s, categoryId: %s, score: %s", locationId, categoryId, score));
        Optional<Area> area = storage.getAreaByCategoryScore(locationId, categoryId, score);
        if (area.isPresent()) {
            return area.get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @POST
    @Path("/result")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Timed
    public Area resultArea(Result result) {
        log.info(String.format("result: %s", result.toJson()));
        Optional<Area> area = storage.getAreaBestMatch(result);
        if (area.isPresent()) {
            return area.get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Timed
    public Area createArea(Area area) {
        log.info(String.format("area: %s", area.toJson()));
        return storage.storeArea(area);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Timed
    public void updateArea(Area area) {
        log.info(String.format("area: %s", area.toJson()));
        storage.updateArea(area);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteArea(@PathParam("id") String id) {
        log.info(String.format("id: %s", id));
        storage.deleteArea(id);
    }
}
