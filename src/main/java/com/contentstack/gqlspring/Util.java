package com.contentstack.gqlspring;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Util {

    public static String load(@NotNull String filename) {
        try {
            File resource = new ClassPathResource("graphql/" + filename).getFile();
            return new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            System.out.println("Exception occurred");
            e.printStackTrace();
        }
        return null;
    }
}

