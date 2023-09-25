package com.ayserjamshidi.newsalerter;

import com.ayserjamshidi.newsalerter.model.NewsAlerterWebSearch;
import com.ayserjamshidi.newsalerter.model.jackson.NewsAlerterConfig;
import com.ayserjamshidi.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class NewsAlerterExecutor {

    private final Logger LOG = LoggerFactory.getLogger(NewsAlerterExecutor.class);
    private final boolean DEBUG = LOG.isDebugEnabled();

    // Autowired Vars
    private final ApplicationContext context;
    //NewsAlerterWebSearch webSearch;

    @Autowired
    NewsAlerterExecutor(ApplicationContext context) {//NewsAlerterWebSearch webSearch) {
//        this.webSearch = webSearch;
        this.context = context;
    }

    public void start(String[] args) {
        LOG.info("DEBUG == {}", DEBUG);

        NewsAlerterConfig newsAlerterConfig;
        try {
            newsAlerterConfig = loadConfig();
        } catch (URISyntaxException | IOException e) {
            LOG.error("An exception occurred while loading the config file");
            return;
        }

        if (newsAlerterConfig == null) {
            LOG.error("Exiting due to the NewsAlerter config being null");
            return;
        }

        execute(newsAlerterConfig.getHeadless(), newsAlerterConfig.getWebsiteEntries());
    }

    /**
     * AYSER TODO
     */
    private NewsAlerterConfig loadConfig() throws URISyntaxException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewsAlerterConfig newsAlerterConfig = null;
        File configFile = FileUtil.getFile("websites.json");

        if (configFile == null) {
            return null;
        }

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
    private void execute(Boolean headless, WebsiteEntry[] websiteEntries) {
        for (WebsiteEntry websiteEntry : websiteEntries) {
            NewsAlerterWebSearch curWebSearch = test();

            curWebSearch.initialize(websiteEntry, headless);
            curWebSearch.start();
        }
    }

    private NewsAlerterWebSearch test() {
        return context.getBean(NewsAlerterWebSearch.class);
    }
}
