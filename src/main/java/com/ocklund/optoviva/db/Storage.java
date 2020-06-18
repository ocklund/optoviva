package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.api.Score;

import java.util.Optional;

public interface Storage {
    Optional<Area> getArea(String areaId);
    Optional<Category> getCategory(String categoryId);
    Optional<Score> getScore(String scoreId);
    String storeArea(Area area);
    String storeCategory(Category category);
    String storeScore(Score score);
    void updateArea(Area area);
    void updateCategory(Category category);
    void updateScore(Score score);
    void deleteArea(String areaId);
    void deleteCategory(String categoryId);
    void deleteScore(String scoreId);
    boolean isOperational();
}
