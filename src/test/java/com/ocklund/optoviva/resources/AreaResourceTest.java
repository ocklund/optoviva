package com.ocklund.optoviva.resources;

import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.db.JdbiStorage;
import com.ocklund.optoviva.db.Storage;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class AreaResourceTest {
    private static final Storage storageMock = mock(JdbiStorage.class);
    private static final ResourceExtension resourceExtension = ResourceExtension.builder()
            .addResource(new AreaResource(storageMock))
            .build();
    private final String areaJson = "{\"id\":null,\"name\":\"foo\",\"description\":\"bar\",\"locationId\":1}";
    private Area area;

    @BeforeEach
    void setup() {
        area = new Area();
        area.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(storageMock);
    }

    @Test
    void shouldGetAreaById() {
        when(storageMock.getArea(1L)).thenReturn(Optional.of(area));

        Area result = resourceExtension.target("/area/1")
                .request()
                .get(Area.class);

        assertThat(result.getId(), is(area.getId()));
        verify(storageMock).getArea(1L);
    }

    @Test
    void shouldSearchArea() {
        when(storageMock.getAreaByCategoryScore(anyLong(), anyLong(), anyInt())).thenReturn(Optional.of(area));

        Area result = resourceExtension.target("/area/search")
                .queryParam("locationId", "1")
                .queryParam("categoryId", "1")
                .queryParam("score", "1")
                .request()
                .get(Area.class);

        assertThat(result.getId(), is(area.getId()));
        verify(storageMock).getAreaByCategoryScore(1L, 1L, 1);
    }

    @Test
    void shouldStoreArea() {
        when(storageMock.storeArea(any(Area.class))).thenReturn(area);

        Area result = resourceExtension.target("/area")
                .request()
                .post(Entity.entity(areaJson, MediaType.APPLICATION_JSON_TYPE), Area.class);

        assertThat(result.getId(), is(area.getId()));
        verify(storageMock).storeArea(any(Area.class));
    }

    @Test
    void shouldUpdateArea() {
        resourceExtension.target("/area")
                .request()
                .put(Entity.entity(areaJson, MediaType.APPLICATION_JSON_TYPE), Area.class);

        verify(storageMock).updateArea(any(Area.class));
    }

    @Test
    void shouldDeleteArea() {
        resourceExtension.target("/area/1").request().delete();

        verify(storageMock).deleteArea(1L);
    }
}