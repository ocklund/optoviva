package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.*;

import java.util.Optional;
import java.util.Set;

public interface Storage {
    Area storeArea(Area area);
    Optional<Area> getArea(String areaId);
    Optional<Area> getAreaByCategoryScore(String locationId, String categoryId, Integer score);
    Optional<Area> getAreaBestMatch(Result result);
    void updateArea(Area area);
    void deleteArea(String areaId);

    Category storeCategory(Category category);
    Optional<Category> getCategory(String categoryId);
    Set<Category> getCategories();
    void updateCategory(Category category);
    void deleteCategory(String categoryId);

    Score storeScore(Score score);
    Optional<Score> getScore(String scoreId);
    void updateScore(Score score);
    void deleteScore(String scoreId);

    Location storeLocation(Location location);
    Optional<Location> getLocation(String locationId);
    Set<Location> getLocations();
    void updateLocation(Location location);
    void deleteLocation(String locationId);

    boolean isOperational();
}
