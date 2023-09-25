package com.ayserjamshidi;

import com.ayserjamshidi.newsalerter.NewsAlerterExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();

    @Autowired
    NewsAlerterExecutor newsAlerterExecutor;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            LOG.error("You must provide an argument!  Possible arguments: {}", "NewsAlerterExecutor");
            return;
        }

        switch (args[0]) {
            case "NewsAlerterExecutor":
                newsAlerterExecutor.start(args);
                break;
            default:
                LOG.error("Unknown argument: {}", args[0]);
                break;
        }

        LOG.info("Main.run() is DONE");
    }
}