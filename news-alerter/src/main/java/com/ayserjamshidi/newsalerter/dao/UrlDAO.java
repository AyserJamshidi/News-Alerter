package com.ayserjamshidi.newsalerter.dao;

import com.ayserjamshidi.newsalerter.model.NewsAlerterDatabaseConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@Repository
public class UrlDAO {
    // Autowired Vars
    ObjectMapper objectMapper;

    //
    private static final Logger LOG = LoggerFactory.getLogger(UrlDAO.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();
//    private final File newsAlerterFilePath;
//    private final NewsAlerterDatabaseConfig newsAlerterDatabaseConfig;
//    private final Set<String> newsAlerterUrls;

    @Autowired
    public UrlDAO(ObjectMapper objectMapper) throws URISyntaxException, IOException {
        this.objectMapper = objectMapper;

//        newsAlerterFilePath = DEBUG ? new ClassPathResource("./configs/websites.json").getFile()
//                : new File(new URI("file:configs/websites.json"));

//        newsAlerterDatabaseConfig = objectMapper.readValue(newsAlerterFilePath, NewsAlerterDatabaseConfig.class);
//        newsAlerterUrls = newsAlerterDatabaseConfig.getUrls();
    }

//    public void addNews(String url) {
//        newsAlerterUrls.add(url);
//
//        try {
//            objectMapper.writeValue(newsAlerterFilePath, newsAlerterUrls);
//        } catch (IOException e) {
//            LOG.error("An exception occurred while saving to the NewsDAOImpl config file", e);
//        }
//    }

//    public Set<String> getNews() {
//        return newsAlerterUrls;
//    }

    public void updateNews() {

    }

    public void deleteNews() {

    }
}
