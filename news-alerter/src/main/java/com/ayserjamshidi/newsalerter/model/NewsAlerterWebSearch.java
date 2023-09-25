package com.ayserjamshidi.newsalerter.model;

import com.ayserjamshidi.newsalerter.WebsiteEntry;
import com.ayserjamshidi.newsalerter.service.NewsSvc;
import org.openqa.selenium.By;
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
    private WebsiteEntry websiteEntry;

    @Autowired
    public NewsAlerterWebSearch(NewsSvc newsSvc) {
        super();
        this.newsSvc = newsSvc;
    }

    public void initialize(WebsiteEntry websiteEntry, boolean headless) {
        super.initialize(headless);
        super.setName(websiteEntry.getName());

        this.websiteEntry = websiteEntry;
        super.websiteUrl = websiteEntry.getUrl();
        super.refreshInterval = websiteEntry.getRefreshInterval();

        LOG.info("Initialized {}", super.getName());
    }

    public void stepsInBetween() {
        List<String> addedUrls = new ArrayList<>();

        for (WebElement webElement : driver.findElements(By.className(websiteEntry.getContainerElement()))) {

            for (WebElement currentItem : webElement.findElements(By.className(websiteEntry.getEntryElement()))) {
                List<String> outputString = new ArrayList<>();

                WebElement titleElement = findElementOrNull(currentItem, By.cssSelector(websiteEntry.getCssSelector()));

                if (titleElement == null) {
                    LOG.warn("Element not found: {}", websiteEntry.getCssSelector());
                    continue;
                }

                String newsUrl = titleElement.getAttribute("href");

                if (newsUrl.isEmpty() || newsSvc.contains(newsUrl))
                    continue;

                outputString.add(titleElement.getText());

                if (newsSvc.contains(newsUrl)) {
                    LOG.info("Already contain {}", newsUrl);
                    continue;
                }

                outputString.add(newsUrl);
                addedUrls.add(newsUrl);

                LOG.info("{}", outputString);
            }
        }

        if (addedUrls.isEmpty()) {
            return;
        }

        LOG.info("Adding {} urls to the database.", addedUrls.size());
        newsSvc.add(addedUrls);
    }
}
