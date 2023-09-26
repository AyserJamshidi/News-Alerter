package com.ayserjamshidi.newsalerter.model.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class NewsElementDetails {
    @JsonProperty("searchBy")
    String searchBy;

    @JsonProperty("searchTerms")
    String searchTerms;
}
