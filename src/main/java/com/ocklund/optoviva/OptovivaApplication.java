package com.ocklund.optoviva;

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
        // TODO: implement application
    }

}
