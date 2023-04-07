package com.ocklund.optoviva.resources;

import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.db.JdbiStorage;
import com.ocklund.optoviva.db.Storage;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class CategoryResourceTest {
    private static final Storage storageMock = mock(JdbiStorage.class);
    private static final ResourceExtension resourceExtension = ResourceExtension.builder()
            .addResource(new CategoryResource(storageMock))
            .build();
    private final String categoryJson = "{\"id\":null,\"name\":\"foo\",\"description\":\"bar\"}";
    private Category category;

    @BeforeEach
    void setup() {
        category = new Category();
        category.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(storageMock);
    }

    @Test
    void shouldGetCategoryById() {
        when(storageMock.getCategory(1L)).thenReturn(Optional.of(category));

        Category result = resourceExtension.target("/category/1")
                .request()
                .get(Category.class);

        assertThat(result.getId(), is(category.getId()));
        verify(storageMock).getCategory(1L);
    }

    @Test
    void shouldGetCategories() {
        when(storageMock.getCategories()).thenReturn(Collections.singleton(category));

        Set<Category> result = resourceExtension.target("/category")
                .request()
                .get(new GenericType<Set<Category>>() {});

        assertThat(result.stream().findFirst().get().getId(), is(category.getId()));
        verify(storageMock).getCategories();
    }

    @Test
    void shouldStoreCategory() {
        when(storageMock.storeCategory(any(Category.class))).thenReturn(category);

        Category result = resourceExtension.target("/category")
                .request()
                .post(Entity.entity(categoryJson, MediaType.APPLICATION_JSON_TYPE), Category.class);

        assertThat(result.getId(), is(category.getId()));
        verify(storageMock).storeCategory(any(Category.class));
    }

    @Test
    void shouldUpdateCategory() {
        resourceExtension.target("/category")
                .request()
                .put(Entity.entity(categoryJson, MediaType.APPLICATION_JSON_TYPE), Category.class);

        verify(storageMock).updateCategory(any(Category.class));
    }

    @Test
    void shouldDeleteCategory() {
        resourceExtension.target("/category/1").request().delete();

        verify(storageMock).deleteCategory(1L);
    }
}