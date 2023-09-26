package com.ayserjamshidi.newsalerter.model.jackson;

import com.ayserjamshidi.newsalerter.WebsiteEntry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@ToString
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsAlerterConfig {
    @JsonProperty("headless")
    Boolean headless;

    @JsonProperty("ignoreFirstRunOutput")
    Boolean ignoreFirstRunOutput;

    @JsonProperty("websites")
    WebsiteEntry[] websiteEntries;
}
