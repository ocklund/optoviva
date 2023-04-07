package com.ocklund.optoviva.resources;

import com.codahale.metrics.annotation.Timed;
import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.db.Storage;
import jakarta.ws.rs.*;
import lombok.extern.java.Log;

import java.util.Optional;
import java.util.Set;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static java.lang.String.format;

@Log
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
    public Category getCategory(@PathParam("id") Long id) {
        log.info(format("id: %s", id));
        Optional<Category> category = storage.getCategory(id);
        if (category.isPresent()) {
            return category.get();
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
    public Category storeCategory(Category category) {
        log.info(format("category: %s", category.toJson()));
        return storage.storeCategory(category);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Timed
    public void updateCategory(Category category) {
        log.info(format("category: %s", category.toJson()));
        storage.updateCategory(category);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteCategory(@PathParam("id") Long id) {
        log.info(format("id: %s", id));
        storage.deleteCategory(id);
    }
}
