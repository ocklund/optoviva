package com.ocklund.optoviva;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

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
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        if (databaseUrl.startsWith("jdbc:h2:")) {
            dataSourceFactory.setUser("optoviva");
            dataSourceFactory.setUrl(databaseUrl);
            dataSourceFactory.setDriverClass("org.h2.Driver");
        } else {
            String user = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            dataSourceFactory.setUser(user);
            dataSourceFactory.setPassword(password);
            dataSourceFactory.setUrl(url);
            dataSourceFactory.setDriverClass("org.postgresql.Driver");
        }
        return dataSourceFactory;
    }
}
