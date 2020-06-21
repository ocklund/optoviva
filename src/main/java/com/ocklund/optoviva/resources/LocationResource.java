package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Location;
import com.ocklund.optoviva.api.Score;
import com.ocklund.optoviva.db.Storage;

import javax.ws.rs.*;

import java.util.List;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/location")
public class LocationResource {

    private final Storage storage;

    public LocationResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Timed
    public Set<Location> getLocations() {
        return storage.getLocations();
    }

    @POST
    @Timed
    public Location createLocation(Location location) {
        return storage.storeLocation(location);
    }

    @PUT
    @Timed
    public void updateLocation(Location location) {
        storage.updateLocation(location);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteLocation(@PathParam("id") String id) {
        storage.deleteLocation(id);
    }
}
