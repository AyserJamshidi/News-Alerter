package com.ayserjamshidi.newsalerter.model;

import com.ayserjamshidi.newsalerter.service.NewsSvc;
import com.ayserjamshidi.template.WebSearch;
import com.ayserjamshidi.newsalerter.WebsiteEntry;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Component
public class SimpleWebSearch extends Thread implements WebSearch {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSearch.class);

    private WebDriver driver;
    WebsiteEntry websiteEntry;
    FirefoxOptions options;
    WebDriverWait wait;

    private NewsSvc newsSvc;

    public SimpleWebSearch() {
        this.setDaemon(false);
        this.setName(this.getClass().getSimpleName());
    }

    @Autowired
    public SimpleWebSearch(NewsSvc newsSvc) {
        this.setDaemon(false);

        this.newsSvc = newsSvc;
        System.out.println(this.newsSvc);
        System.out.println(this.newsSvc.contains(""));
    }

    /**
     * Initializes the {@link WebDriver} in standard mode
     */
    public void initialize() {
        this.initialize(false);
    }

    /**
     * Initializes the {@link WebDriver} in either standard or performance mode based on
     * the {@code performanceMode} parameter.
     *
     * @param performanceMode determines if the function is in performance mode or not
     */
    @Override
    public void initialize(Boolean performanceMode) {
        if (!performanceMode) {
            driver = WebDriverManager.firefoxdriver().create();
        } else {
            options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addPreference("browser.tabs.remote.autostart", false);
            options.setLogLevel(FirefoxDriverLogLevel.ERROR);

            driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            LOG.debug("{} reloading...", this.getName());

            driver.get(websiteEntry.getUrl());

            try {
                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
            } catch (TimeoutException e) {
                LOG.warn("Document not ready!");
                continue;
            }

            for (WebElement webElement : driver.findElements(By.className(websiteEntry.getContainerElement()))) {

                for (WebElement currentItem : webElement.findElements(By.className(websiteEntry.getEntryElement()))) {
                    List<String> outputString = new ArrayList<>();

                    WebElement titleElement = findElementOrNull(currentItem, By.cssSelector(websiteEntry.getCssSelector()));

                    if (titleElement == null) {
                        LOG.warn("Element not found: {}", websiteEntry.getCssSelector());
                        continue;
                    }

                    String outputHref = titleElement.getAttribute("href");
                    outputString.add(titleElement.getText());

                    if (outputHref.isEmpty())
                        continue;

                    if (newsSvc.contains(outputHref)) {
                        LOG.info("Already contain {}", outputHref);
                        continue;
                    }

                    outputString.add(outputHref);

                    LOG.info("{}", outputString);
                }
            }

            sleepNoException(websiteEntry.getRefreshInterval());
        }

        driver.quit();
    }

    protected void outputEntry() {

    }

    protected <E> WebElement findElementOrNull(E object, By by) {
        try {
            if (object instanceof WebElement)
                return ((WebElement) object).findElement(by);
            else if (object instanceof WebDriver)
                return ((WebDriver) object).findElement(by);
            else {
                LOG.warn("We are searching with an object we don't know about!");
                return null;
            }
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Sleeps for the specified number of milliseconds without throwing an exception.
     *
     * @param millis the number of milliseconds to sleep
     */
    protected void sleepNoException(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Do nothing
        }
    }
}
