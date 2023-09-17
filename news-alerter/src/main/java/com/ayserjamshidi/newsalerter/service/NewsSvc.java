package com.ayserjamshidi.newsalerter.service;

import com.ayserjamshidi.newsalerter.dao.UrlDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NewsSvc {
    private final UrlDAO urlDAO;

    private static final Logger LOG = LoggerFactory.getLogger(UrlDAO.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();

    @Autowired
    public NewsSvc(UrlDAO urlDAO) {
        this.urlDAO = urlDAO;
    }

    public boolean contains(String url) {
        return urlDAO.getNews().contains(url);
    }
}
