package com.ayserjamshidi;

//import com.ayserjamshidi.newsalerter.factory.SimpleWebSearchFactory;
import com.ayserjamshidi.newsalerter.model.NewsAlerterConfig;
import com.ayserjamshidi.newsalerter.WebsiteEntry;
import com.ayserjamshidi.newsalerter.model.SimpleWebSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class Main {
//    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
//    private static final boolean DEBUG = LOG.isDebugEnabled();

    ObjectMapper objectMapper;
    @Autowired
    SimpleWebSearch webSearch;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}