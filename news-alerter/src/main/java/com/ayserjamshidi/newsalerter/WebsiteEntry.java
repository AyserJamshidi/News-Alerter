package com.ayserjamshidi.newsalerter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Setter
@Getter
@Builder
@ToString
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsiteEntry {

    @JsonProperty("name")
    @NonNull
    String name;

    @JsonProperty("url")
    @NonNull
    String url;

    @JsonProperty("refreshInterval")
    int refreshInterval;

    @JsonProperty("containerElement")
    @NonNull
    String containerElement;

    @JsonProperty("entryElement")
    @NonNull
    String entryElement;

    @JsonProperty("cssSelector")
    String cssSelector;

    @JsonProperty("outputTitleUrl")
    Boolean outputTitleUrl;

    @JsonProperty("blacklistElements")
    String[] blacklistElements;
}
