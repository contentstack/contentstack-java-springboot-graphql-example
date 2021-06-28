package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlogListModel {
    @JsonProperty
    public String title;
    @JsonProperty
    public String url;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date date;
    @JsonProperty
    public String body;
    @JsonProperty
    public Object authorConnection;
    @JsonProperty
    public Object featured_imageConnection;

}
