package com.ayserjamshidi.newsalerter.dao;

import com.ayserjamshidi.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UrlDAO {

    // Statics
    private static final Logger LOG = LoggerFactory.getLogger(UrlDAO.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();

    // Autowired Vars
    ObjectMapper objectMapper;

    //
    private final String FILE_NAME = "news_database.json";
    private final File newsAlerterFile;
    private final Set<String> storedUrls;

    @Autowired
    public UrlDAO(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;

        File tempFile = FileUtil.getConfigFile(FILE_NAME);

        if (tempFile == null) {
            LOG.error("Could not find database file '{}/{}', creating an empty one.", System.getProperty("user.dir"), FILE_NAME);

            newsAlerterFile = FileUtil.getOrCreateConfigFile(FILE_NAME);
            storedUrls = new HashSet<>();
            writeToFile();
        } else {
            newsAlerterFile = tempFile;
            storedUrls = objectMapper.readValue(newsAlerterFile, HashSet.class);
        }
    }

    public synchronized void addNews(String url) {
        storedUrls.add(url);
        writeToFile();
    }

    public synchronized void addNews(List<String> urls) {
        storedUrls.addAll(urls);
        writeToFile();
    }

    private void writeToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(newsAlerterFile, storedUrls);
        } catch (IOException e) {
            LOG.error("An exception occurred while saving to the NewsDAOImpl config file", e);
        }
    }

    public synchronized Set<String> getNews() {
        return storedUrls;
    }
}
