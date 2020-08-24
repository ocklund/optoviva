package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Score;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ScoreDao {
    @SqlUpdate("insert into score (area_id, category_id, location_id, score) " +
               "values (:areaId, :categoryId, :locationId, :score)")
    @GetGeneratedKeys("id")
    long insert(@Bind("areaId") Long areaId,
                @Bind("categoryId") Long categoryId,
                @Bind("locationId") Long locationId,
                @Bind("score") Integer score);

    @SqlQuery("select * from score where id = :id")
    @RegisterFieldMapper(Score.class)
    Score findById(@Bind("id") Long id);

    @SqlUpdate("update score set area_id = :areaId, category_id = :categoryId, location_id = :locationId, score = :score " +
               "where id = :id")
    void update(@BindBean Score score);

    @SqlUpdate("delete from score where id = :id")
    void delete(@Bind("id") Long id);
}
