package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.db.Storage;

import javax.ws.rs.*;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class AreaResource {

    private final Storage storage;

    public AreaResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Path("/area/{id}")
    @Timed
    public Area getArea(@PathParam("id") String id) {
        if (storage.getArea(id).isPresent()) {
            return storage.getArea(id).get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @POST
    @Path("/area")
    @Timed
    public String createArea(@PathParam("area") Area area) {
        return storage.storeArea(area);
    }

    @PUT
    @Path("/area")
    @Timed
    public void updateArea(@PathParam("area") Area area) {
        storage.updateArea(area);
    }

    @DELETE
    @Path("/area/{id}")
    @Timed
    public void deleteArea(@PathParam("id") Area area) {
        storage.updateArea(area);
    }
}
