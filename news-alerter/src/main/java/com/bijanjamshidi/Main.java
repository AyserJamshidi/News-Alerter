package com.bijanjamshidi;

import com.bijanjamshidi.model.WebsiteConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URL;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        logger.info("Hello world!");
        loadConfig();
    }

    static void loadConfig() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            WebsiteConfig websiteConfig;

            if (LOG.isDebugEnabled())
                websiteConfig = objectMapper.readValue(new ClassPathResource("./configs/websites.json").getFile(), WebsiteConfig.class);
            else
                websiteConfig = objectMapper.readValue(new URL("file:configs/websites.json"), WebsiteConfig.class);

            LOG.debug(websiteConfig.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}