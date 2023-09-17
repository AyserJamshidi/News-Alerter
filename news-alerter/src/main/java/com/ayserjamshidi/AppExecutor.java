package com.ayserjamshidi;

import com.ayserjamshidi.newsalerter.WebsiteEntry;
import com.ayserjamshidi.newsalerter.factory.SimpleWebSearchFactory;
import com.ayserjamshidi.newsalerter.model.NewsAlerterConfig;
import com.ayserjamshidi.newsalerter.model.SimpleWebSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class AppExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(AppExecutor.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();

    public void test() {
        NewsAlerterConfig newsAlerterConfig = null;
        try {
            newsAlerterConfig = loadConfig();
        } catch (URISyntaxException | IOException e) {
            LOG.error("An exception occurred while loading the config file");
            return;
        }

        execute(newsAlerterConfig.getHeadless(), newsAlerterConfig.getWebsiteEntries());
    }

    /**
     * AYSER TODO
     */
    static NewsAlerterConfig loadConfig() throws URISyntaxException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewsAlerterConfig newsAlerterConfig = null;
        File configFile = DEBUG ? new ClassPathResource("./configs/websites.json").getFile() : new File(new URI("file:configs/websites.json"));
        try {
            newsAlerterConfig = objectMapper.readValue(configFile, NewsAlerterConfig.class);
            LOG.debug(newsAlerterConfig.toString());
        } catch (IOException e) {
            LOG.error("An exception occurred while loading the config file", e);
        }

        return newsAlerterConfig;
    }

    /**
     * Executes a unique thread for each {@link WebsiteEntry} in an array of website entries.
     *
     * @param websiteEntries an array of {@link WebsiteEntry} representing the websites to be executed
     */
    private static void execute(Boolean headless, WebsiteEntry[] websiteEntries) {
        for (WebsiteEntry websiteEntry : websiteEntries) {
            SimpleWebSearch testy = SimpleWebSearchFactory.createSimpleWebSearch(websiteEntry);

            testy.initialize(headless);
            testy.start();
        }
    }
}
