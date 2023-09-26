package com.ayserjamshidi.newsalerter;

import com.ayserjamshidi.newsalerter.model.jackson.NewsElementDetails;
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
    @NonNull
    Integer refreshInterval;

    @JsonProperty("containerElement")
    @NonNull
    NewsElementDetails containerElement;

    @JsonProperty("singlePostElement")
    @NonNull
    NewsElementDetails singlePostElement;

    @JsonProperty("urlElement")
    @NonNull
    NewsElementDetails urlElement;

    @JsonProperty("titleElement")
    NewsElementDetails titleElement;

    @JsonProperty("blacklistElements")
    String[] blacklistElements;
}
