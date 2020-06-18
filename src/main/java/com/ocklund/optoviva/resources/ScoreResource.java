package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Score;
import com.ocklund.optoviva.db.Storage;

import javax.ws.rs.*;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class ScoreResource {

    private final Storage storage;

    public ScoreResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Path("/score/{id}")
    @Timed
    public Score getScore(@PathParam("id") String id) {
        if (storage.getScore(id).isPresent()) {
            return storage.getScore(id).get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @POST
    @Path("/score")
    @Timed
    public String createScore(@PathParam("score") Score score) {
        return storage.storeScore(score);
    }

    @PUT
    @Path("/score")
    @Timed
    public void updateScore(@PathParam("score") Score score) {
        storage.updateScore(score);
    }

    @DELETE
    @Path("/score/{id}")
    @Timed
    public void deleteScore(@PathParam("id") Score score) {
        storage.updateScore(score);
    }
}
