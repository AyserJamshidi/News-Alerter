package com.ayserjamshidi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    // Finals
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();
    private static final String CONFIG_DIRECTORY = "config";

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    public static File getFile(String fileName) {
        Resource resource = resourceLoader.getResource((DEBUG ? "classpath:" : "file:") + fileName);
        File output = null;

        if (resource.exists()) {
            try {
                output = resource.getFile();
            } catch (IOException e) {
                LOG.error("An exception occurred while trying to get the resource '{}'", output);
            }
        }
        return output;
    }

    public static File getConfigFile(String fileName) {
        return getFile(CONFIG_DIRECTORY + '/' + fileName);
    }

    public static File getOrCreateConfigFile(String fileName) {
        // Create config directory if needed
        Path configPath = Paths.get(CONFIG_DIRECTORY + '/' + fileName);

        try {
            Files.createDirectories(configPath.getParent());
        } catch (IOException e) {
            LOG.error("An error occurred when trying to create the config directory '{}'", CONFIG_DIRECTORY, e);
        }

        if (!Files.exists(configPath)) {
            try {
                Files.createFile(configPath);
            } catch (IOException e) {
                LOG.error("An error occurred while trying to create the config file '{}'", configPath, e);
            }
        }

        return getConfigFile(fileName);
    }
}
