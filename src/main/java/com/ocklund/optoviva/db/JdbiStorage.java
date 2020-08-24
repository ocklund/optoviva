package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Area;
import com.ocklund.optoviva.api.Category;
import com.ocklund.optoviva.api.Location;
import com.ocklund.optoviva.api.Score;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;
import java.util.Set;

public class JdbiStorage implements Storage {

    private final Jdbi jdbi;

    public JdbiStorage(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Area storeArea(Area area) {
        final AreaDao areaDao = jdbi.onDemand(AreaDao.class);
        long id = areaDao.insert(area.getName(), area.getDescription(), area.getLocationId());
        area.setId(id);
        return area;
    }

    @Override
    public Optional<Area> getArea(Long areaId) {
        final AreaDao areaDao = jdbi.onDemand(AreaDao.class);
        return Optional.ofNullable(areaDao.findById(areaId));
    }

    @Override
    public Optional<Area> getAreaByCategoryScore(Long locationId, Long categoryId, Integer score) {
        final AreaDao areaDao = jdbi.onDemand(AreaDao.class);
        return Optional.ofNullable(areaDao.findByCategoryScore(locationId, categoryId, score));
    }

    @Override
    public void updateArea(Area area) {
        final AreaDao areaDao = jdbi.onDemand(AreaDao.class);
        areaDao.update(area);
    }

    @Override
    public void deleteArea(Long areaId) {
        final AreaDao areaDao = jdbi.onDemand(AreaDao.class);
        areaDao.delete(areaId);
    }

    @Override
    public Category storeCategory(Category category) {
        final CategoryDao categoryDao = jdbi.onDemand(CategoryDao.class);
        long id = categoryDao.insert(category.getName(), category.getDescription());
        category.setId(id);
        return category;
    }

    @Override
    public Optional<Category> getCategory(Long categoryId) {
        final CategoryDao categoryDao = jdbi.onDemand(CategoryDao.class);
        return Optional.ofNullable(categoryDao.findById(categoryId));
    }

    @Override
    public Set<Category> getCategories() {
        final CategoryDao categoryDao = jdbi.onDemand(CategoryDao.class);
        return categoryDao.findAll();
    }

    @Override
    public void updateCategory(Category category) {
        final CategoryDao categoryDao = jdbi.onDemand(CategoryDao.class);
        categoryDao.update(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        final CategoryDao categoryDao = jdbi.onDemand(CategoryDao.class);
        categoryDao.delete(categoryId);
    }

    @Override
    public Score storeScore(Score score) {
        final ScoreDao scoreDao = jdbi.onDemand(ScoreDao.class);
        long id = scoreDao.insert(score.getAreaId(), score.getCategoryId(), score.getLocationId(), score.getScore());
        score.setId(id);
        return score;
    }

    @Override
    public Optional<Score> getScore(Long scoreId) {
        final ScoreDao scoreDao = jdbi.onDemand(ScoreDao.class);
        return Optional.ofNullable(scoreDao.findById(scoreId));
    }

    @Override
    public void updateScore(Score score) {
        final ScoreDao scoreDao = jdbi.onDemand(ScoreDao.class);
        scoreDao.update(score);
    }

    @Override
    public void deleteScore(Long scoreId) {
        final ScoreDao scoreDao = jdbi.onDemand(ScoreDao.class);
        scoreDao.delete(scoreId);
    }

    @Override
    public Location storeLocation(Location location) {
        final LocationDao locationDao = jdbi.onDemand(LocationDao.class);
        long id = locationDao.insert(location.getName(), location.getDescription());
        location.setId(id);
        return location;
    }

    @Override
    public Optional<Location> getLocation(Long locationId) {
        final LocationDao locationDao = jdbi.onDemand(LocationDao.class);
        return Optional.ofNullable(locationDao.findById(locationId));
    }

    @Override
    public Set<Location> getLocations() {
        final LocationDao locationDao = jdbi.onDemand(LocationDao.class);
        return locationDao.findAll();
    }

    @Override
    public void updateLocation(Location location) {
        final LocationDao locationDao = jdbi.onDemand(LocationDao.class);
        locationDao.update(location);
    }

    @Override
    public void deleteLocation(Long locationId) {
        final LocationDao locationDao = jdbi.onDemand(LocationDao.class);
        locationDao.delete(locationId);
    }

    @Override
    public void loadData(String sql) {
        try (Handle handle = jdbi.open()) {
            handle.execute(sql);
        }
    }

    @Override
    public boolean isOperational() {
        final LocationDao locationDao = jdbi.onDemand(LocationDao.class);
        try {
            locationDao.testConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
