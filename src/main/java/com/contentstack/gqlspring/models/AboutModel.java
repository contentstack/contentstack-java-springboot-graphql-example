package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The type Product.
 */
@Data
public class AboutModel {

    /**
     * The Title.
     */
    @JsonProperty
    String title;

    /**
     * The Description.
     */
    @JsonProperty
    String description;

}
