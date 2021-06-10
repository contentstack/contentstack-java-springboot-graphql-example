package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HomeModel {
    @JsonProperty
    public String title;
    @JsonProperty
    public String url;
    @JsonProperty
    public Object seo;
    @JsonProperty
    public Object page_components;
}