package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.db.Storage;

import javax.ws.rs.*;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/category")
public class CategoryResource {

    private final Storage storage;

    public CategoryResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    @Timed
    public Category getCategory(@PathParam("id") String id) {
        if (storage.getCategory(id).isPresent()) {
            return storage.getCategory(id).get();
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Timed
    public Set<Category> getCategories() {
        return storage.getCategories();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Timed
    public Category createCategory(Category category) {
        return storage.storeCategory(category);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Timed
    public void updateCategory(Category category) {
        storage.updateCategory(category);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteCategory(@PathParam("id") String id) {
        storage.deleteCategory(id);
    }
}
