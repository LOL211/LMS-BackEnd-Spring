package com.kush.banbah.soloprojectbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileConfig {

    @Bean
    public Path rootLocation() throws IOException {
        Path rootLocation = Paths.get("uploadDir");
        Files.createDirectories(rootLocation);
        return rootLocation;
    }
}
