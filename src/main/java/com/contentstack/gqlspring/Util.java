package com.contentstack.gqlspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The type Util.
 */
public class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class.getName());

    /**
     * Fetch product string.
     *
     * @return the string
     */
    public static String fetchProduct() {
        String content = null;
        try {
            File file = ResourceUtils.getFile("classpath:product.graphql");
            content = new String(Files.readAllBytes(file.toPath()));
            LOGGER.debug("file: " + content);
            return content;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return content;
    }
}