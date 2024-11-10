package org.andersen.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StateConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = StateConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStateFilePath() {
        return properties.getProperty("state.file.path");
    }

    public static boolean isApartmentStatusChangeEnabled() {
        return Boolean.parseBoolean(properties.getProperty("apartment.status.change.enabled"));
    }
}
