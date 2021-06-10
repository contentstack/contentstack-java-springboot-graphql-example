package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlogPostModel {
    @JsonProperty
    public String title;
    @JsonProperty
    public String url;
    @JsonProperty
    public Object seo;
    @JsonProperty
    public String date;
    @JsonProperty
    public String body;
    @JsonProperty
    public Object authorConnection;
    @JsonProperty
    public Object related_postConnection;
}
