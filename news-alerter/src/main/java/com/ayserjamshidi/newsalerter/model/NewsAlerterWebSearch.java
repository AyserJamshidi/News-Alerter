package com.ayserjamshidi.newsalerter.model;

import com.ayserjamshidi.newsalerter.WebsiteEntry;
import com.ayserjamshidi.newsalerter.model.jackson.NewsElementDetails;
import com.ayserjamshidi.newsalerter.service.NewsSvc;
import com.ayserjamshidi.util.SeleniumUtil;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NewsAlerterWebSearch extends SimpleWebSearch {

    private final Logger LOG = LoggerFactory.getLogger(NewsAlerterWebSearch.class);

    // Autowired
    private final NewsSvc newsSvc;

    // NewsAlerter Specific
//    private WebsiteEntry websiteEntry;
    private NewsElementDetails containerDetails;
    private NewsElementDetails singlePostDetails;
    private NewsElementDetails urlDetails;
    private NewsElementDetails titleDetails;
    private boolean ignoreFirstRunOutput = false;

    @Autowired
    public NewsAlerterWebSearch(NewsSvc newsSvc) {
        super();
        this.newsSvc = newsSvc;
    }

    public void initialize(WebsiteEntry websiteEntry, boolean ignoreFirstRunOutput, boolean headless) {
        super.initialize(headless);
        super.setName(websiteEntry.getName());

//        this.websiteEntry = websiteEntry;
        super.websiteUrl = websiteEntry.getUrl();
        super.refreshInterval = websiteEntry.getRefreshInterval();

        this.containerDetails = websiteEntry.getContainerElement();
        this.singlePostDetails = websiteEntry.getSinglePostElement();
        this.urlDetails = websiteEntry.getUrlElement();
        this.titleDetails = websiteEntry.getTitleElement();
        this.ignoreFirstRunOutput = ignoreFirstRunOutput;

        LOG.info("Initialized {}", super.getName());
    }

    public void stepsInBetween() {
        List<String> addedUrls = new ArrayList<>();

        for (WebElement containerElement : driver.findElements(SeleniumUtil.getBy(containerDetails.getSearchBy(), containerDetails.getSearchTerms()))) {

            for (WebElement currentPost : containerElement.findElements(SeleniumUtil.getBy(singlePostDetails.getSearchBy(), singlePostDetails.getSearchTerms()))) {
                StringBuilder outputString = new StringBuilder();
                WebElement urlElement = findElementOrNull(currentPost, SeleniumUtil.getBy(urlDetails.getSearchBy(), urlDetails.getSearchTerms()));
                WebElement titleElement = titleDetails != null ? findElementOrNull(currentPost, SeleniumUtil.getBy(titleDetails.getSearchBy(), titleDetails.getSearchTerms())) : null;

                if (urlElement == null) {
                    LOG.error("Failed to acquire URL element for '{}'", this.getName());
                    continue;
                }

                String newsUrlText = urlElement.getAttribute("href");
                String titleText = titleElement != null ? titleElement.getText() : null;

                if (newsUrlText.isEmpty() || newsSvc.contains(newsUrlText))
                    continue;

                addedUrls.add(newsUrlText);

                if (titleText != null && !titleText.isEmpty()) {
                    outputString.append(titleText).append(' ');
                }
                outputString.append(newsUrlText);
                LOG.info(outputString.toString());
            }
        }

        if (addedUrls.isEmpty()) {
            return;
        }

        LOG.debug("Adding {} urls to the database.", addedUrls.size());
        newsSvc.add(addedUrls);
    }
}
