package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Area;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageTest {

    Storage storage = new InMemoryStorage();

    @Test
    void shouldGetAreaById() {
        Optional<Area> area = storage.getArea("1");
        assertEquals("Södermalm", area.get().getName());
    }

    @Test
    void shouldGetAreaByCategoryScore() {
        Optional<Area> area = storage.getAreaByCategoryScore("1", "1", 2);
        assertEquals("Södermalm", area.get().getName());
    }
}