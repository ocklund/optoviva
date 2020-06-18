package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.db.Storage;

import javax.ws.rs.*;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class CategoryResource {

    private final Storage storage;

    public CategoryResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Path("/category/{id}")
    @Timed
    public Category getCategory(@PathParam("id") String id) {
        if (storage.getCategory(id).isPresent()) {
            return storage.getCategory(id).get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @POST
    @Path("/category")
    @Timed
    public String createCategory(@PathParam("category") Category category) {
        return storage.storeCategory(category);
    }

    @PUT
    @Path("/category")
    @Timed
    public void updateCategory(@PathParam("category") Category category) {
        storage.updateCategory(category);
    }

    @DELETE
    @Path("/category/{id}")
    @Timed
    public void deleteCategory(@PathParam("id") Category category) {
        storage.updateCategory(category);
    }
}
