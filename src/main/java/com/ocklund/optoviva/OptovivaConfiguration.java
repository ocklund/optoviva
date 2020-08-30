package com.ocklund.optoviva;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;

public class OptovivaConfiguration extends Configuration {
    @Valid
    @NotNull
    private final DataSourceFactory database = createDataSourceFactory(System.getenv("DATABASE_URL"));

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    private static DataSourceFactory createDataSourceFactory(String databaseUrl) {
        if (databaseUrl == null) {
            throw new IllegalArgumentException("DATABASE_URL environment variable must be set");
        }
        URI dbUri;
        try {
            dbUri = new URI(databaseUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("DATABASE_URL environment variable must be a valid URI", e);
        }
        String user = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setUser(user);
        dataSourceFactory.setPassword(password);
        dataSourceFactory.setUrl(url);
        dataSourceFactory.setDriverClass("org.postgresql.Driver");
        return dataSourceFactory;
    }
}
