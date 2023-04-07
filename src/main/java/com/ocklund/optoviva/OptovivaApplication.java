package com.ocklund.optoviva;

import com.ocklund.optoviva.cli.LoadDataCommand;
import com.ocklund.optoviva.db.JdbiStorage;
import com.ocklund.optoviva.db.Storage;
import com.ocklund.optoviva.health.StorageHealthCheck;
import com.ocklund.optoviva.resources.AreaResource;
import com.ocklund.optoviva.resources.CategoryResource;
import com.ocklund.optoviva.resources.LocationResource;
import com.ocklund.optoviva.resources.ScoreResource;
import io.dropwizard.core.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jdbi.v3.core.Jdbi;

import java.util.EnumSet;

public class OptovivaApplication extends Application<OptovivaConfiguration> {

    public static void main(final String[] args) throws Exception {
        new OptovivaApplication().run(args);
    }

    @Override
    public String getName() {
        return "Optoviva";
    }

    @Override
    public void initialize(final Bootstrap<OptovivaConfiguration> bootstrap) {
        bootstrap.addBundle(new JdbiExceptionsBundle());
        bootstrap.addBundle(new MigrationsBundle<OptovivaConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(OptovivaConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
        bootstrap.addCommand(new LoadDataCommand(this));
    }

    @Override
    public void run(final OptovivaConfiguration configuration, final Environment environment) {
        setCors(environment);
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "jdbi");
        final Storage storage = new JdbiStorage(jdbi);
        environment.jersey().register(new AreaResource(storage));
        environment.jersey().register(new CategoryResource(storage));
        environment.jersey().register(new ScoreResource(storage));
        environment.jersey().register(new LocationResource(storage));
        environment.healthChecks().register("storage", new StorageHealthCheck(storage));
    }

    private void setCors(final Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "Cache-Control,If-Modified-Since,Pragma,Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
