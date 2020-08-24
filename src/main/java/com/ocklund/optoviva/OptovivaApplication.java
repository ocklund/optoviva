package com.ocklund.optoviva;

import com.ocklund.optoviva.cli.LoadDataCommand;
import com.ocklund.optoviva.db.JdbiStorage;
import com.ocklund.optoviva.db.Storage;
import com.ocklund.optoviva.health.StorageHealthCheck;
import com.ocklund.optoviva.resources.AreaResource;
import com.ocklund.optoviva.resources.CategoryResource;
import com.ocklund.optoviva.resources.LocationResource;
import com.ocklund.optoviva.resources.ScoreResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

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
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "jdbi");
        final Storage storage = new JdbiStorage(jdbi);
        environment.jersey().register(new AreaResource(storage));
        environment.jersey().register(new CategoryResource(storage));
        environment.jersey().register(new ScoreResource(storage));
        environment.jersey().register(new LocationResource(storage));
        environment.healthChecks().register("storage", new StorageHealthCheck(storage));
    }
}
