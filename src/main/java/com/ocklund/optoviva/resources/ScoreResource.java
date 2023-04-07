package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Score;
import com.ocklund.optoviva.db.Storage;
import jakarta.ws.rs.*;
import lombok.extern.java.Log;

import java.util.Optional;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static java.lang.String.format;

@Log
@Path("/score")
public class ScoreResource {

    private final Storage storage;

    public ScoreResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Timed
    @Path("/{id}")
    public Score getScore(@PathParam("id") Long id) {
        log.info(format("id: %s", id));
        Optional<Score> score = storage.getScore(id);
        if (score.isPresent()) {
            return score.get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Timed
    public Score storeScore(Score score) {
        log.info(format("score: %s", score.toJson()));
        return storage.storeScore(score);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Timed
    public void updateScore(Score score) {
        log.info(format("score: %s", score.toJson()));
        storage.updateScore(score);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteScore(@PathParam("id") Long id) {
        log.info(format("id: %s", id));
        storage.deleteScore(id);
    }
}
