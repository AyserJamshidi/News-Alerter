package com.ayserjamshidi.newsalerter.service;

import com.ayserjamshidi.newsalerter.dao.UrlDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsSvc {

    private static final Logger LOG = LoggerFactory.getLogger(UrlDAO.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();

    // Autowired
    private final UrlDAO urlDAO;

    @Autowired
    public NewsSvc(UrlDAO urlDAO) {
        this.urlDAO = urlDAO;
    }

    public boolean contains(String url) {
        return urlDAO.getNews().contains(url);
    }

    public void add(String url) {
        urlDAO.addNews(url);
    }

    public void add(List<String> urls) {
        urlDAO.addNews(urls);
    }
}
