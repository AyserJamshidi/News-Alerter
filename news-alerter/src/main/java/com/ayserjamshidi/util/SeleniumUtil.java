package com.ayserjamshidi.util;

import org.openqa.selenium.By;

public class SeleniumUtil {
    public static By getBy(String byStr, String searchTerms) {
        return switch (byStr) {
            case "id" -> By.id(searchTerms);
            case "name" -> By.name(searchTerms);
            case "linkText" -> By.linkText(searchTerms);
            case "partialLinkText" -> By.partialLinkText(searchTerms);
            case "tagName" -> By.tagName(searchTerms);
            case "className" -> By.className(searchTerms);
            case "cssSelector" -> By.cssSelector(searchTerms);
            case "xpath" -> By.xpath(searchTerms);
            default -> throw new IllegalArgumentException("Unknown by: " + byStr);
        };
    }
}
