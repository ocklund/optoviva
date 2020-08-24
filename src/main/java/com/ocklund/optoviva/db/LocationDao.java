package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Location;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Set;

public interface LocationDao {
    @SqlUpdate("insert into location (name, description) values (:name, :description)")
    @GetGeneratedKeys("id")
    long insert(@Bind("name") String name, @Bind("description") String description);

    @SqlQuery("select * from location where id = :id")
    @RegisterFieldMapper(Location.class)
    Location findById(@Bind("id") Long id);

    @SqlQuery("select * from location")
    @RegisterFieldMapper(Location.class)
    Set<Location> findAll();

    @SqlUpdate("update location set name = :name, description = :description where id = :id")
    void update(@BindBean Location location);

    @SqlUpdate("delete from location where id = :id")
    void delete(@Bind("id") Long id);

    @SqlQuery("select 1 from location")
    int testConnection();
}
