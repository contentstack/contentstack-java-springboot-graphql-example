package com.contentstack.gqlspring;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class Util {

    private Util() {
        throw new IllegalStateException("Private constructor = not allowed");
    }

    public static String load(@NotNull String filename) {
        try {
            File resource = new ClassPathResource("graphql/" + filename).getFile();
            return new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            Logger.getLogger("Error in load", e.getLocalizedMessage());
        }
        return null;
    }
}
