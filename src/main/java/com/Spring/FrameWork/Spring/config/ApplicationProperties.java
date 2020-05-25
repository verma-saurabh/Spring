package com.Spring.FrameWork.Spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreInvalidFields = false)
public class ApplicationProperties {
    private final Batch batch = new Batch();

    public Batch getBatch() {
        return batch;
    }

    public static class Batch {
        public String inputPath = "";

        public String getInputpath() {
            return this.inputPath;
        }

        public void setInputPath(String path) {
            this.inputPath = path;
        }
    }
}
