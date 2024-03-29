package com.ocklund.optoviva.cli;

import com.ocklund.optoviva.OptovivaApplication;
import com.ocklund.optoviva.OptovivaConfiguration;
import io.dropwizard.core.cli.Cli;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.util.JarLocation;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

@Log
class LoadDataCommandTest {
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream stdErr = new ByteArrayOutputStream();
    private Cli cli;

    @BeforeEach
    void setUp() {
        final JarLocation jarLocation = mock(JarLocation.class);
        when(jarLocation.getVersion()).thenReturn(Optional.of("1.0.0"));
        final Bootstrap<OptovivaConfiguration> bootstrap = new Bootstrap<>(new OptovivaApplication());
        bootstrap.addCommand(new LoadDataCommand(bootstrap.getApplication()));
        System.setOut(new PrintStream(stdOut));
        System.setErr(new PrintStream(stdErr));
        cli = new Cli(jarLocation, bootstrap, stdOut, stdErr);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void shouldLoadData() throws Exception {
        String path = "src/test/resources/";
        withEnvironmentVariable("DATABASE_URL", "jdbc:h2:./target/testdb").execute(() -> {
            Optional<Throwable> error = cli.run("load", "-d", path + "data.sql", "config.yml");

            assertThat(error.isPresent(), is(false));
            assertThat(stdOut.toString(UTF_8), containsString("Successfully loaded data"));
            assertThat(stdErr.toString(UTF_8), is(""));
        });
    }
}