package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Score;
import com.ocklund.optoviva.db.Storage;

import javax.ws.rs.*;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/score")
public class ScoreResource {

    private final Storage storage;

    public ScoreResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Timed
    @Path("/{id}}")
    public Score getScore(@PathParam("id") String id) {
        if (storage.getScore(id).isPresent()) {
            return storage.getScore(id).get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @POST
    @Timed
    public String createScore(Score score) {
        return storage.storeScore(score);
    }

    @PUT
    @Timed
    public void updateScore(Score score) {
        storage.updateScore(score);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteScore(@PathParam("id") String id) {
        storage.deleteScore(id);
    }
}
