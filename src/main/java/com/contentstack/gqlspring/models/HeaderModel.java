package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HeaderModel {

    @JsonProperty
    public Object logoConnection;
    @JsonProperty
    public Object navigation_menu;
    @JsonProperty
    public Object notification_bar;
    @JsonProperty
    public String title;

}