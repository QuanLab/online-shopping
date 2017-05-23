package com.quanpv.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String UPLOAD_FOLDER;

    public String getUPLOAD_FOLDER() {
        return UPLOAD_FOLDER;
    }

    public void setUPLOAD_FOLDER(String UPLOAD_FOLDER) {
        this.UPLOAD_FOLDER = UPLOAD_FOLDER;
    }
}
