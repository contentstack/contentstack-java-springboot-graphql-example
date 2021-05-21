package com.contentstack.gqlspring;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The type Product.
 */
@Data
public class Product {

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
