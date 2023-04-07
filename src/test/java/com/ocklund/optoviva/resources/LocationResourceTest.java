package com.ocklund.optoviva.resources;

import com.ocklund.optoviva.api.Location;
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
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class LocationResourceTest {
    private static final Storage storageMock = mock(JdbiStorage.class);
    private static final ResourceExtension resourceExtension = ResourceExtension.builder()
            .addResource(new LocationResource(storageMock))
            .build();
    private final String locationJson = "{\"id\":null,\"name\":\"foo\",\"description\":\"bar\"}";
    private Location location;

    @BeforeEach
    void setup() {
        location = new Location();
        location.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(storageMock);
    }

    @Test
    void shouldGetLocations() {
        when(storageMock.getLocations()).thenReturn(Collections.singleton(location));

        Set<Location> result = resourceExtension.target("/location")
                .request()
                .get(new GenericType<Set<Location>>() {});

        assertThat(result.stream().findFirst().get().getId(), is(location.getId()));
        verify(storageMock).getLocations();
    }

    @Test
    void shouldStoreLocation() {
        when(storageMock.storeLocation(any(Location.class))).thenReturn(location);

        Location result = resourceExtension.target("/location")
                .request()
                .post(Entity.entity(locationJson, MediaType.APPLICATION_JSON_TYPE), Location.class);

        assertThat(result.getId(), is(location.getId()));
        verify(storageMock).storeLocation(any(Location.class));
    }

    @Test
    void shouldUpdateLocation() {
        resourceExtension.target("/location")
                .request()
                .put(Entity.entity(locationJson, MediaType.APPLICATION_JSON_TYPE), Location.class);

        verify(storageMock).updateLocation(any(Location.class));
    }

    @Test
    void shouldDeleteLocation() {
        resourceExtension.target("/location/1").request().delete();

        verify(storageMock).deleteLocation(1L);
    }
}