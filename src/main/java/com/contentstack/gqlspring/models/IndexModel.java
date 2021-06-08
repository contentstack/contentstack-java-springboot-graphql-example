package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IndexModel {

    @JsonProperty
    public Object logoConnection;
    @JsonProperty
    public Object navigation;
    @JsonProperty
    public String title;
    @JsonProperty
    public Object social;

}
