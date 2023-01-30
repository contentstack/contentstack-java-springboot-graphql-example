package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlogPostModel {
    @JsonProperty
    public String title;
    @JsonProperty
    public String url;
    @JsonProperty
    public Object seo;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date date;
    @JsonProperty
    public String body;
    @JsonProperty
    public Object authorConnection;
    @JsonProperty("related_postConnection")
    public Object relatedPostConnection;
}