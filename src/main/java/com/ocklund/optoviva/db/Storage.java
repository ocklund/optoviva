package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.*;

import java.util.Optional;
import java.util.Set;

public interface Storage {
    Area storeArea(Area area);
    Optional<Area> getArea(Long areaId);
    Optional<Area> getAreaByCategoryScore(Long locationId, Long categoryId, Integer score);
    void updateArea(Area area);
    void deleteArea(Long areaId);

    Category storeCategory(Category category);
    Optional<Category> getCategory(Long categoryId);
    Set<Category> getCategories();
    void updateCategory(Category category);
    void deleteCategory(Long categoryId);

    Score storeScore(Score score);
    Optional<Score> getScore(Long scoreId);
    void updateScore(Score score);
    void deleteScore(Long scoreId);

    Location storeLocation(Location location);
    Optional<Location> getLocation(Long locationId);
    Set<Location> getLocations();
    void updateLocation(Location location);
    void deleteLocation(Long locationId);

    void loadData(String sql);
    boolean isOperational();
}
