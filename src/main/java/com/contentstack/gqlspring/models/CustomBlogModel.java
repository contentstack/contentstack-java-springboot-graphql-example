package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomBlogModel {
    @JsonProperty
    public String title;
    @JsonProperty
    public Object page_components;
}
