package com.ayserjamshidi.newsalerter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.HashSet;

@Getter
@Builder
@ToString
@Jacksonized
public class NewsAlerterDatabaseConfig {
    @JsonProperty("urls")
    HashSet<String> urls;
}
