package com.ocklund.optoviva.cli;

import com.ocklund.optoviva.OptovivaConfiguration;
import com.ocklund.optoviva.db.JdbiStorage;
import com.ocklund.optoviva.db.Storage;
import io.dropwizard.Application;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import lombok.extern.java.Log;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.jdbi.v3.core.Jdbi;

import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.lang.String.join;

@Log
public class LoadDataCommand extends EnvironmentCommand<OptovivaConfiguration> {

    public LoadDataCommand(Application<OptovivaConfiguration> application) {
        super(application,"load", "Loads data into database");
    }

    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
        subparser.addArgument("-d", "--data-file");
    }

    @Override
    protected void run(Environment environment, Namespace namespace, OptovivaConfiguration configuration) throws Exception {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "load");
        final Storage storage = new JdbiStorage(jdbi);
        final String sql = join("\n", Files.readAllLines(Paths.get(namespace.getString("data_file"))));
        storage.loadData(sql);
        int locationsCount = storage.getLocations().size();
        int categoriesCount = storage.getCategories().size();
        log.info(format("Successfully loaded data, %s locations and %s categories", locationsCount, categoriesCount));
    }
}