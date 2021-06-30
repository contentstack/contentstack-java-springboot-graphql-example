package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArchivedModel {
    @JsonProperty
    public String title;
    @JsonProperty
    public String url;
    @JsonProperty
    public String body;
}
