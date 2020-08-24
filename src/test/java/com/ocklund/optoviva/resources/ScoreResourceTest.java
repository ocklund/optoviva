package com.ocklund.optoviva.resources;

import com.ocklund.optoviva.api.Score;
import com.ocklund.optoviva.db.JdbiStorage;
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
class ScoreResourceTest {
    private static final Storage storageMock = mock(JdbiStorage.class);
    private static final ResourceExtension resourceExtension = ResourceExtension.builder()
            .addResource(new ScoreResource(storageMock))
            .build();
    private final String scoreJson = "{\"id\":null,\"areaId\":1,\"categoryId\":1,\"locationId\":1,\"score\":1}";
    private Score score;

    @BeforeEach
    void setup() {
        score = new Score();
        score.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(storageMock);
    }

    @Test
    void shouldGetScoreById() {
        when(storageMock.getScore(1L)).thenReturn(Optional.of(score));

        Score result = resourceExtension.target("/score/1")
                .request()
                .get(Score.class);

        assertThat(result.getId(), is(score.getId()));
        verify(storageMock).getScore(1L);
    }

    @Test
    void shouldStoreScore() {
        when(storageMock.storeScore(any(Score.class))).thenReturn(score);

        Score result = resourceExtension.target("/score")
                .request()
                .post(Entity.entity(scoreJson, MediaType.APPLICATION_JSON_TYPE), Score.class);

        assertThat(result.getId(), is(score.getId()));
        verify(storageMock).storeScore(any(Score.class));
    }

    @Test
    void shouldUpdateScore() {
        resourceExtension.target("/score")
                .request()
                .put(Entity.entity(scoreJson, MediaType.APPLICATION_JSON_TYPE), Score.class);

        verify(storageMock).updateScore(any(Score.class));
    }

    @Test
    void shouldDeleteScore() {
        resourceExtension.target("/score/1").request().delete();

        verify(storageMock).deleteScore(1L);
    }
}