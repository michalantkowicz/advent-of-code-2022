package com.advent.of.code;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Utils {
    private final static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static List<String> readFileLines(String filePath) {
        try {
            return FileUtils.readLines(new File(filePath), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Error during file loading [" + filePath + "]", e);
            throw new RuntimeException(e);
        }
    }
}
