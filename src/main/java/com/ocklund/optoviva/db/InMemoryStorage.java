package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.*;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log
public class InMemoryStorage implements Storage {
    Map<String, Location> locationStorage = new HashMap<>();
    Map<String, Category> categoryStorage = new HashMap<>();
    Map<String, Area> areaStorage = new HashMap<>();
    Map<String, Score> scoreStorage = new HashMap<>();

    public InMemoryStorage() {
        log.info("Loading data");
        String timestamp = LocalDateTime.now().toString();
        locationStorage.put("1", new Location("1", "Stockholm", "Capitol of Sweden", timestamp, timestamp, "system"));
        locationStorage.put("2", new Location("2", "Barcelona", "Capitol of Catalonia", timestamp, timestamp, "system"));

        categoryStorage.put("1", new Category("1", "Beaches", "How close to nice beaches or the water (sea, lake) this area is.", timestamp, timestamp, "system"));
        categoryStorage.put("2", new Category("2", "Communications", "How close to communications this area is (metro, bus, trains, etc).", timestamp, timestamp, "system"));
        categoryStorage.put("3", new Category("3", "Family friendly", "How family friendly the area is (schools, playgrounds, etc).", timestamp, timestamp, "system"));
        categoryStorage.put("4", new Category("4", "Green areas", "How much green areas (parks, natural reserves, etc) there are in the area.", timestamp, timestamp, "system"));
        categoryStorage.put("5", new Category("5", "Cost", "How high the cost of living is in the area.", timestamp, timestamp, "system"));
        categoryStorage.put("6", new Category("6", "Nightlife", "How much nightlife there is in the area (restaurants, bars, nightclubs, etc).", timestamp, timestamp, "system"));

        areaStorage.put("1", new Area("1", "Södermalm", "Famously bohemian area, now taken over by hipsters", "1", timestamp, timestamp, "system"));
        areaStorage.put("2", new Area("2", "Nacka", "New area for well-to-do people", "1", timestamp, timestamp, "system"));
        areaStorage.put("3", new Area("3", "Kungsholmen", "Classic area for well-to-do people", "1", timestamp, timestamp, "system"));
        areaStorage.put("4", new Area("4", "Danderyd", "Exclusive area with high income households", "1", timestamp, timestamp, "system"));
        areaStorage.put("5", new Area("5", "Norrmalm", "Exclusive area in the city center", "1", timestamp, timestamp, "system"));

        // Södermalm
        scoreStorage.put("1", new Score("1", "1", "1", "1", 2, timestamp, timestamp, "system"));
        scoreStorage.put("2", new Score("2", "1", "2", "1", 4, timestamp, timestamp, "system"));
        scoreStorage.put("3", new Score("3", "1", "3", "1", 3, timestamp, timestamp, "system"));
        scoreStorage.put("4", new Score("4", "1", "4", "1", 3, timestamp, timestamp, "system"));
        scoreStorage.put("5", new Score("5", "1", "5", "1", 4, timestamp, timestamp, "system"));
        scoreStorage.put("6", new Score("6", "1", "6", "1", 5, timestamp, timestamp, "system"));
        // Nacka
        scoreStorage.put("7", new Score("7", "2", "1", "1", 5, timestamp, timestamp, "system"));
        scoreStorage.put("8", new Score("8", "2", "2", "1", 2, timestamp, timestamp, "system"));
        scoreStorage.put("9", new Score("9", "2", "3", "1", 4, timestamp, timestamp, "system"));
        scoreStorage.put("10", new Score("10", "2", "4", "1", 4, timestamp, timestamp, "system"));
        scoreStorage.put("11", new Score("11", "2", "5", "1", 4, timestamp, timestamp, "system"));
        scoreStorage.put("12", new Score("12", "2", "6", "1", 2, timestamp, timestamp, "system"));
        log.info(String.format("Location size: %s", locationStorage.size()));
        log.info(String.format("Area size: %s", areaStorage.size()));
        log.info(String.format("Category size: %s", categoryStorage.size()));
        log.info(String.format("Score size: %s", scoreStorage.size()));
        log.info("Finished loading data");
    }

    @Override
    public Area storeArea(Area area) {
        String id = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().toString();
        area.setId(id);
        area.setCreated(timestamp);
        area.setModified(timestamp);
        areaStorage.put(id, area);
        return area;
    }

    @Override
    public Optional<Area> getArea(String areaId) {
        return Optional.ofNullable(areaStorage.get(areaId));
    }

    @Override
    public Optional<Area> getAreaByCategoryScore(String locationId, String categoryId, Integer scoreInt) {
        Optional<Score> scoreOptional = scoreStorage.values().stream()
                .filter(score -> locationId.equals(score.getLocationId()) &&
                        categoryId.equals(score.getCategoryId()) &&
                        scoreInt.equals(score.getScore()))
                .findFirst();
        return scoreOptional.map(score -> areaStorage.get(score.getAreaId()));
    }

    @Override
    public Optional<Area> getAreaBestMatch(Result result) {
        log.info(String.format("result: %s", result.toJson()));
        List<String> allIds = result.getChoices().stream()
                .flatMap(choice -> choice.getAreaIds().stream())
                .collect(Collectors.toList());
        Optional<String> idOptional = mostFrequentId(allIds);
        return idOptional.map(id -> areaStorage.get(id));
    }

    @Override
    public void updateArea(Area area) {
        String timestamp = LocalDateTime.now().toString();
        area.setModified(timestamp);
        areaStorage.put(area.getId(), area);
    }

    @Override
    public void deleteArea(String areaId) {
        areaStorage.remove(areaId);
    }

    @Override
    public Category storeCategory(Category category) {
        String id = UUID.randomUUID().toString();
        category.setId(id);
        categoryStorage.put(id, category);
        return category;
    }

    @Override
    public Optional<Category> getCategory(String categoryId) {
        return Optional.ofNullable(categoryStorage.get(categoryId));
    }

    @Override
    public Set<Category> getCategories() {
        return categoryStorage.values().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void updateCategory(Category category) {
        String timestamp = LocalDateTime.now().toString();
        category.setModified(timestamp);
        categoryStorage.put(category.getId(), category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryStorage.remove(categoryId);
    }

    @Override
    public Score storeScore(Score score) {
        String id = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().toString();
        score.setId(id);
        score.setCreated(timestamp);
        score.setModified(timestamp);
        scoreStorage.put(id, score);
        return score;
    }

    @Override
    public Optional<Score> getScore(String scoreId) {
        return Optional.ofNullable(scoreStorage.get(scoreId));
    }

    @Override
    public void updateScore(Score score) {
        String timestamp = LocalDateTime.now().toString();
        score.setModified(timestamp);
        scoreStorage.put(score.getId(), score);
    }

    @Override
    public void deleteScore(String scoreId) {
        scoreStorage.remove(scoreId);
    }

    @Override
    public Location storeLocation(Location location) {
        String id = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().toString();
        location.setId(id);
        location.setCreated(timestamp);
        location.setModified(timestamp);
        locationStorage.put(id, location);
        return location;
    }

    @Override
    public Optional<Location> getLocation(String locationId) {
        return Optional.ofNullable(locationStorage.get(locationId));
    }

    @Override
    public Set<Location> getLocations() {
        return locationStorage.values().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void updateLocation(Location location) {
        String timestamp = LocalDateTime.now().toString();
        location.setModified(timestamp);
        locationStorage.put(location.getId(), location);
    }

    @Override
    public void deleteLocation(String locationId) {
        locationStorage.remove(locationId);
    }

    @Override
    public boolean isOperational() {
        return true;
    }

    private Optional<String> mostFrequentId(List<String> ids) {
        Set<String> uniqueIds = new HashSet<>(ids);
        int highestFrequency = 0;
        String mostFrequentId = null;
        for (String id : uniqueIds) {
            int frequency = Collections.frequency(ids, id);
            if (frequency > highestFrequency) {
                mostFrequentId = id;
            }
        }
        return Optional.ofNullable(mostFrequentId);
    }
}
