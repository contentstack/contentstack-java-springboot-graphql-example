package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The type Blog model.
 */
@Data
public class BlogModel {

    @JsonProperty
    private String date;
    @JsonProperty
    private String body;
    @JsonProperty
    private String title;
    @JsonProperty
    private String url;
}
