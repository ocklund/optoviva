package com.ocklund.optoviva.db;

import com.ocklund.optoviva.api.Category;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Set;

public interface CategoryDao {
    @SqlUpdate("insert into category (name, description) values (:name, :description)")
    @GetGeneratedKeys("id")
    long insert(@Bind("name") String name, @Bind("description") String description);

    @SqlQuery("select * from category where id = :id")
    @RegisterFieldMapper(Category.class)
    Category findById(@Bind("id") Long id);

    @SqlQuery("select * from category")
    @RegisterFieldMapper(Category.class)
    Set<Category> findAll();

    @SqlUpdate("update category set name = :name, description = :description where id = :id")
    void update(@BindBean Category category);

    @SqlUpdate("delete from category where id = :id")
    void delete(@Bind("id") Long id);
}
