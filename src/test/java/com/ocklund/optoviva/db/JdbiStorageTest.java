package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.api.Location;
import com.ocklund.optoviva.api.Score;
import io.dropwizard.util.Resources;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

import static io.dropwizard.util.Resources.getResource;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class JdbiStorageTest {

    @TempDir
    public static File tempDir;
    private static JdbiStorage jdbiStorage;

    @BeforeAll
    static void setUp() throws IOException {
        File dbFile = new File(tempDir, "testdb");
        Jdbi jdbi = Jdbi.create("jdbc:h2:" + dbFile.getAbsolutePath(), "optoviva", "");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbiStorage = new JdbiStorage(jdbi);
        jdbiStorage.loadData(Resources.toString(getResource("data.sql"), StandardCharsets.UTF_8));
    }

    @Test
    void storeArea() {
        final Long tempId = 999L;
        final String name = "area name";
        Area area = new Area(tempId, name, "area description", 1L);

        Area result = jdbiStorage.storeArea(area);

        assertNotEquals(tempId, result.getId());
        assertEquals(name, result.getName());
        assertEquals(result.getName(), jdbiStorage.getArea(result.getId()).get().getName());
    }

    @Test
    void getArea() {
        Optional<Area> result = jdbiStorage.getArea(1L);

        assertEquals("Danderyd", result.get().getName());
    }

    @Test
    void getAreaByCategoryScore() {
        Optional<Area> result = jdbiStorage.getAreaByCategoryScore(1L, 3L, 4);

        assertEquals("Ekerö", result.get().getName());
    }

    @Test
    void updateArea() {
        final String updatedName = "updated name";
        final String updatedDescription = "updated description";
        final Long updatedLocationId = 3L;
        Area area = new Area(999L, "area name", "area description", 999L);
        Area storedArea = jdbiStorage.storeArea(area);
        storedArea.setName(updatedName);
        storedArea.setDescription(updatedDescription);
        storedArea.setLocationId(updatedLocationId);

        jdbiStorage.updateArea(storedArea);
        Optional<Area> result = jdbiStorage.getArea(storedArea.getId());

        assertEquals(updatedName, result.get().getName());
        assertEquals(updatedDescription, result.get().getDescription());
        assertEquals(updatedLocationId, result.get().getLocationId());
    }

    @Test
    void deleteArea() {
        final Long tempId = 999L;
        final String name = "area to be deleted";

        Area area = new Area(tempId, name, "area description", tempId);
        Area storedArea = jdbiStorage.storeArea(area);
        assertEquals(name, jdbiStorage.getArea(storedArea.getId()).get().getName());

        jdbiStorage.deleteArea(storedArea.getId());
        Optional<Area> result = jdbiStorage.getArea(storedArea.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void storeCategory() {
        final Long tempId = 999L;
        final String name = "category name";
        Category category = new Category(tempId, name, "category description");

        Category result = jdbiStorage.storeCategory(category);

        assertNotEquals(tempId, result.getId());
        assertEquals(name, result.getName());
        assertEquals(result.getName(), jdbiStorage.getCategory(result.getId()).get().getName());

        // Cleanup.
        jdbiStorage.deleteCategory(result.getId());
    }

    @Test
    void getCategory() {
        Optional<Category> result = jdbiStorage.getCategory(1L);

        assertEquals("Communication", result.get().getName());
    }

    @Test
    void getCategories() {
        Set<Category> result = jdbiStorage.getCategories();

        assertEquals(6, result.size());
        assertEquals(1L, result.stream().filter(c -> "Nightlife".equals(c.getName())).count());
    }

    @Test
    void updateCategory() {
        final String updatedName = "updated name";
        final String updatedDescription = "updated description";
        Category category = new Category(999L, "category name", "category description");
        Category storedCategory = jdbiStorage.storeCategory(category);
        storedCategory.setName(updatedName);
        storedCategory.setDescription(updatedDescription);

        jdbiStorage.updateCategory(storedCategory);
        Optional<Category> result = jdbiStorage.getCategory(storedCategory.getId());

        assertEquals(updatedName, result.get().getName());
        assertEquals(updatedDescription, result.get().getDescription());

        // Cleanup.
        jdbiStorage.deleteCategory(storedCategory.getId());
    }

    @Test
    void deleteCategory() {
        final Long tempId = 999L;
        final String name = "category to be deleted";

        Category category = new Category(tempId, name, "category description");
        Category storedCategory = jdbiStorage.storeCategory(category);
        assertEquals(name, jdbiStorage.getCategory(storedCategory.getId()).get().getName());

        jdbiStorage.deleteCategory(storedCategory.getId());
        Optional<Category> result = jdbiStorage.getCategory(storedCategory.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void storeScore() {
        final Long tempId = 999L;
        final Long areaId = 99L;
        final Long categoryId = 99L;
        final Long locationId = 99L;
        final Integer areaCategoryScore = 100;
        Score score = new Score(tempId, areaId, categoryId, locationId, areaCategoryScore);

        Score result = jdbiStorage.storeScore(score);

        assertNotEquals(tempId, result.getId());
        assertEquals(areaId, result.getAreaId());
        assertEquals(categoryId, result.getCategoryId());
        assertEquals(locationId, result.getLocationId());
        assertEquals(areaCategoryScore, result.getScore());
        assertEquals(result.getAreaId(), jdbiStorage.getScore(result.getId()).get().getAreaId());
    }

    @Test
    void getScore() {
        Optional<Score> result = jdbiStorage.getScore(1L);

        assertEquals(3, result.get().getScore());
    }

    @Test
    void updateScore() {
        final Long updatedAreaId = 999L;
        final Long updatedCategoryId = 999L;
        final Long updatedLocationId = 999L;
        final Integer updatedScore = 101;
        Score score = new Score(999L, 888L, 888L, 888L, 100);
        Score storedScore = jdbiStorage.storeScore(score);
        storedScore.setAreaId(updatedAreaId);
        storedScore.setCategoryId(updatedCategoryId);
        storedScore.setLocationId(updatedLocationId);
        storedScore.setScore(updatedScore);

        jdbiStorage.updateScore(storedScore);
        Optional<Score> result = jdbiStorage.getScore(storedScore.getId());

        assertEquals(updatedAreaId, result.get().getAreaId());
        assertEquals(updatedCategoryId, result.get().getCategoryId());
        assertEquals(updatedLocationId, result.get().getLocationId());
        assertEquals(updatedScore, result.get().getScore());
    }

    @Test
    void deleteScore() {
        final Long tempId = 999L;
        final Integer areaCategoryScore = 1;
        Score score = new Score(tempId, 999L, 999L, 999L, areaCategoryScore);
        Score storedScore = jdbiStorage.storeScore(score);
        assertEquals(areaCategoryScore, jdbiStorage.getScore(storedScore.getId()).get().getScore());

        jdbiStorage.deleteScore(storedScore.getId());
        Optional<Score> result = jdbiStorage.getScore(storedScore.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void storeLocation() {
        final Long tempId = 999L;
        final String name = "location name";
        final String description = "location description";
        Location location = new Location(tempId, name, description);

        Location result = jdbiStorage.storeLocation(location);

        assertNotEquals(tempId, result.getId());
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
        assertEquals(result.getName(), jdbiStorage.getLocation(result.getId()).get().getName());
    }

    @Test
    void getLocation() {
        Optional<Location> result = jdbiStorage.getLocation(1L);

        assertEquals("Stockholm", result.get().getName());
    }

    @Test
    void getLocations() {
        Set<Location> result = jdbiStorage.getLocations();

        assertEquals(2, result.size());
        assertEquals(1L, result.stream().filter(c -> "Barcelona".equals(c.getName())).count());
    }

    @Test
    void updateLocation() {
        final String updatedName = "updated name";
        final String updatedDescription = "updated description";
        Location location = new Location(999L, "location name", "location description");
        Location storedLocation = jdbiStorage.storeLocation(location);
        storedLocation.setName(updatedName);
        storedLocation.setDescription(updatedDescription);

        jdbiStorage.updateLocation(storedLocation);
        Optional<Location> result = jdbiStorage.getLocation(storedLocation.getId());

        assertEquals(updatedName, result.get().getName());
        assertEquals(updatedDescription, result.get().getDescription());

        // Cleanup.
        jdbiStorage.deleteLocation(storedLocation.getId());
    }

    @Test
    void deleteLocation() {
        final Long tempId = 999L;
        final String name = "location to be deleted";
        Location location = new Location(tempId, name, "location description");
        Location storedLocation = jdbiStorage.storeLocation(location);
        assertEquals(name, jdbiStorage.getLocation(storedLocation.getId()).get().getName());

        jdbiStorage.deleteLocation(storedLocation.getId());
        Optional<Location> result = jdbiStorage.getLocation(storedLocation.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void isOperational() {
        assertTrue(jdbiStorage.isOperational());
    }
}