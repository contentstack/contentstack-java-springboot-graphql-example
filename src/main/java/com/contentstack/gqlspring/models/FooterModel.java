package com.contentstack.gqlspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The type Product.
 */
public class FooterModel {

    /**
     * The Title.
     */
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FooterModel{" +
                "title='" + title + '\'' +
                '}';
    }
}
