package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.api.Score;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryStorage implements Storage {

    Map<String, Area> areaStorage = new HashMap<>();
    Map<String, Category> categoryStorage = new HashMap<>();
    Map<String, Score> scoreStorage = new HashMap<>();

    @Override
    public Optional<Area> getArea(String areaId) {
        return Optional.ofNullable(areaStorage.get(areaId));
    }

    @Override
    public Optional<Category> getCategory(String categoryId) {
        return Optional.ofNullable(categoryStorage.get(categoryId));
    }

    @Override
    public Optional<Score> getScore(String scoreId) {
        return Optional.ofNullable(scoreStorage.get(scoreId));
    }

    @Override
    public String storeArea(Area area) {
        String id = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().toString();
        area.setId(id);
        area.setCreated(timestamp);
        area.setModified(timestamp);
        areaStorage.put(id, area);
        return id;
    }

    @Override
    public String storeCategory(Category category) {
        String id = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().toString();
        category.setId(id);
        category.setCreated(timestamp);
        category.setModified(timestamp);
        categoryStorage.put(id, category);
        return id;
    }

    @Override
    public String storeScore(Score score) {
        String id = UUID.randomUUID().toString();
        String timestamp = LocalDateTime.now().toString();
        score.setId(id);
        score.setCreated(timestamp);
        score.setModified(timestamp);
        scoreStorage.put(id, score);
        return id;
    }

    @Override
    public void updateArea(Area area) {
        areaStorage.put(area.getId(), area);
    }

    @Override
    public void updateCategory(Category category) {
        categoryStorage.put(category.getId(), category);
    }

    @Override
    public void updateScore(Score score) {
        scoreStorage.put(score.getId(), score);
    }

    @Override
    public void deleteArea(String areaId) {
        areaStorage.remove(areaId);
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryStorage.remove(categoryId);
    }

    @Override
    public void deleteScore(String scoreId) {
        scoreStorage.remove(scoreId);
    }

    @Override
    public boolean isOperational() {
        return true;
    }
}
