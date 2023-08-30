package com.bijanjamshidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class WebsiteConfig {
    @JsonProperty("websites")
    Website[] websites;
}
