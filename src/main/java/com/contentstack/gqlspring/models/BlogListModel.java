package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BlogListModel {
    @JsonProperty
    public String title;
    @JsonProperty
    public String url;
    @JsonProperty
    public String date;
    @JsonProperty
    public String body;
    @JsonProperty
    public Object authorConnection;
    @JsonProperty
    public Object featured_imageConnection;
}
