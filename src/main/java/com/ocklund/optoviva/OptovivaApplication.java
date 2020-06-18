package com.ocklund.optoviva;

import com.ocklund.optoviva.db.InMemoryStorage;
import com.ocklund.optoviva.db.Storage;
import com.ocklund.optoviva.health.StorageHealthCheck;
import com.ocklund.optoviva.resources.AreaResource;
import com.ocklund.optoviva.resources.CategoryResource;
import com.ocklund.optoviva.resources.ScoreResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // TODO: application initialization
    }

    @Override
    public void run(final OptovivaConfiguration configuration,
                    final Environment environment) {
        final Storage storage = new InMemoryStorage();
        environment.healthChecks().register("storage", new StorageHealthCheck(storage));
        environment.jersey().register(new AreaResource(storage));
        environment.jersey().register(new CategoryResource(storage));
        environment.jersey().register(new ScoreResource(storage));
    }
}
