package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Location;
import com.ocklund.optoviva.db.Storage;
import lombok.extern.java.Log;

import javax.ws.rs.*;
import java.util.Set;

import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Log
@Path("/location")
public class LocationResource {

    private final Storage storage;

    public LocationResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Timed
    public Set<Location> getLocations() {
        return storage.getLocations();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Timed
    public Location storeLocation(Location location) {
        log.info(format("location: %s", location.toJson()));
        return storage.storeLocation(location);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Timed
    public void updateLocation(Location location) {
        log.info(format("location: %s", location.toJson()));
        storage.updateLocation(location);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteLocation(@PathParam("id") Long id) {
        log.info(format("id: %s", id));
        storage.deleteLocation(id);
    }
}
