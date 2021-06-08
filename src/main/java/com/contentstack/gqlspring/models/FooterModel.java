package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Product.
 */
@Data
@AllArgsConstructor
public class FooterModel {

    @JsonProperty
    String title;
}
