package com.ayserjamshidi.newsalerter.model;

import com.ayserjamshidi.template.WebSearch;
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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE) // Required to create a new instance when this bean is requested
public abstract class SimpleWebSearch extends Thread implements WebSearch {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSearch.class);

    protected WebDriver driver;
//    protected EdgeOptions options;
    protected FirefoxOptions options;
    protected String websiteUrl;
    protected int refreshInterval;
    protected WebDriverWait wait;

    public SimpleWebSearch() {
        this.setDaemon(false);
        this.setName(this.getClass().getSimpleName());
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
//        options = new EdgeOptions();
        options = new FirefoxOptions();

        if (performanceMode) {
//            options.addArguments("headless");
//            options.addArguments("disablegpu");


            // Firefox
            options.addArguments("--headless");
            options.addPreference("browser.tabs.remote.autostart", false);
            options.setLogLevel(FirefoxDriverLogLevel.ERROR);
        }

        driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Override
    public void run() {
        if (websiteUrl.isEmpty()) {
            LOG.error("Website URL is not set or was empty!");
        }

        while (!this.isInterrupted()) {
            LOG.info("Reloading '{}'", this.getName());
            driver.get(websiteUrl);

            try {
                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
            } catch (TimeoutException e) {
                LOG.warn("Document not ready!");
                continue;
            }

            stepsInBetween();

            sleepNoException(refreshInterval);
        }

        driver.quit();
    }

    protected void stepsInBetween() {

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
