package com.birds.nn.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class ConfigLoader {
    private static final Logger LOGGER = Logger.getLogger(ConfigLoader.class.getName());

    public static Config loadConfig(String filename) {
        Gson gson = new Gson();
        Config config = null;
        try (FileReader reader = new FileReader(filename)) {
            config = gson.fromJson(reader, Config.class);
            LOGGER.info("Configuration loaded successfully from " + filename);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load configuration from " + filename, e);
        }
        return config;
    }
}
