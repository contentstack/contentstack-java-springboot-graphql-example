package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HeaderModel {

    @JsonProperty
    public Object logoConnection;
    @JsonProperty("navigation_menu")
    public Object navigationMenu;
    @JsonProperty("notification_bar")
    public Object notificationBar;
    @JsonProperty
    public String title;

}