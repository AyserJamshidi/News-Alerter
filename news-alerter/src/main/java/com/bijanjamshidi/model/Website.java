package com.bijanjamshidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Setter
@Getter
@Builder
@ToString
@Jacksonized
public class Website {

    @JsonProperty("name")
    String name;

    @JsonProperty("url")
    String url;

    @JsonProperty("refreshInterval")
    int refreshInterval;

    @JsonProperty("primaryContainer")
    String primaryContainer;

    @JsonProperty("desiredElement")
    String desiredElement;

    @JsonProperty("blacklistElements")
    String[] blacklistElements;
}
