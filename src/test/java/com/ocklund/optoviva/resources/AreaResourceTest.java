package com.ocklund.optoviva.resources;

import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.api.Result;
import com.ocklund.optoviva.db.InMemoryStorage;
import com.ocklund.optoviva.db.Storage;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class AreaResourceTest {
    private static final Storage storageMock = mock(InMemoryStorage.class);
    private static final ResourceExtension resourceExtension = ResourceExtension.builder()
            .addResource(new AreaResource(storageMock))
            .build();
    private Area area;

    @BeforeEach
    void setup() {
        area = new Area();
        area.setId("1");
    }

    @AfterEach
    void tearDown() {
        reset(storageMock);
    }

    @Test
    void shouldGetAreaById() {
        when(storageMock.getArea("1")).thenReturn(Optional.of(area));

        Area result = resourceExtension.target("/area/1")
                .request()
                .get(Area.class);

        assertThat(result.getId(), is(area.getId()));
        verify(storageMock).getArea("1");
    }

    @Test
    void shouldSearchArea() {
        when(storageMock.getAreaByCategoryScore(anyString(), anyString(), anyInt())).thenReturn(Optional.of(area));

        Area result = resourceExtension.target("/area/search")
                .queryParam("locationId", "1")
                .queryParam("categoryId", "1")
                .queryParam("score", "1")
                .request()
                .get(Area.class);

        assertThat(result.getId(), is(area.getId()));
        verify(storageMock).getAreaByCategoryScore("1", "1", 1);
    }

    @Test
    void shouldStoreArea() {
        when(storageMock.storeArea(any(Area.class))).thenReturn(area);
        String areaJson = "{\"id\":null,\"name\":\"foo\",\"description\":\"bar\",\"locationId\":\"1\",\"created\":null,\"modified\":null,\"modifiedBy\":\"test\"}";

        Area result = resourceExtension.target("/area")
                .request()
                .post(Entity.entity(areaJson, MediaType.APPLICATION_JSON_TYPE), Area.class);

        assertThat(result.getId(), is(area.getId()));
        verify(storageMock).storeArea(any(Area.class));
    }

    @Test
    void shouldGetAreaBestMatch() {
        when(storageMock.getAreaBestMatch(any(Result.class))).thenReturn(Optional.of(area));
        String resultJson =
                "{" +
                "   \"email\": \"jane.doe@example.com\"," +
                "   \"locationId\": \"1\"," +
                "   \"choices\": [{" +
                "       \"categoryId\": \"1\"," +
                "       \"score\": 1," +
                "       \"areaId\": 2" +
                "   }]" +
                "}";

        Area result = resourceExtension.target("/area/result")
                .request()
                .post(Entity.entity(resultJson, MediaType.APPLICATION_JSON_TYPE), Area.class);

        assertThat(result.getId(), is(area.getId()));
        verify(storageMock).getAreaBestMatch(any(Result.class));
    }

    @Test
    void shouldUpdateArea() {
        String areaJson = "{\"id\":null,\"name\":\"foo\",\"description\":\"bar\",\"locationId\":\"1\",\"created\":null,\"modified\":null,\"modifiedBy\":\"test\"}";

        resourceExtension.target("/area")
                .request()
                .put(Entity.entity(areaJson, MediaType.APPLICATION_JSON_TYPE), Area.class);

        verify(storageMock).updateArea(any(Area.class));
    }
}