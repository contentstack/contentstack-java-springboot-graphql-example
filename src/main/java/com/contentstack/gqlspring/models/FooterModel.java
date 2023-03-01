package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.minidev.json.JSONObject;

@Data
public class FooterModel {

    @JsonProperty
    public Object logoConnection;
    @JsonProperty
    public Object navigation;
    @JsonProperty
    public String title;
    @JsonProperty
    public JSONObject copyright;
    @JsonProperty
    public Object social;

}