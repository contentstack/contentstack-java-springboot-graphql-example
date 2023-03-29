package com.contentstack.gqlspring;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());

    private Util() {
    }

    public static String load(@NotNull String filename) {
        try {
            File resource = new ClassPathResource("graphql/" + filename).getFile();
            return new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, ex.getLocalizedMessage());
        }
        return null;
    }
}
