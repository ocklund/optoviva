package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Area;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AreaDao {
    @SqlUpdate("insert into area (name, description, location_id) values (:name, :description, :locationId)")
    @GetGeneratedKeys("id")
    long insert(@Bind("name") String name, @Bind("description") String description, @Bind("locationId") Long locationId);

    @SqlQuery("select * from area where id = :id")
    @RegisterFieldMapper(Area.class)
    Area findById(@Bind("id") Long id);

    @SqlQuery("select a.id, a.name, a.description, a.location_id from area a, score s " +
              "where a.id = s.area_id " +
              "and s.location_id = :locationId " +
              "and s.category_id = :categoryId " +
              "and s.score = :score")
    @RegisterFieldMapper(Area.class)
    Area findByCategoryScore(@Bind("locationId") Long locationId,
                             @Bind("categoryId") Long categoryId,
                             @Bind("score") Integer score);

    @SqlUpdate("update area set name = :name, description = :description, location_id = :locationId where id = :id")
    void update(@BindBean Area area);

    @SqlUpdate("delete from area where id = :id")
    void delete(@Bind("id") Long id);
}
