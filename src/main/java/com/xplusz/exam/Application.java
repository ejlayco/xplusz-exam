package com.xplusz.exam;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.social.TwitterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootApplication
public class Application {

    @Inject
    private TwitterProperties twitterProperties;

    @Inject
    private ApplicationProperties applicationProperties;

    @Bean
    public String credentials() throws UnsupportedEncodingException {
        String charset = StandardCharsets.UTF_8.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(URLEncoder.encode(twitterProperties.getAppId(), charset))
                .append(":")
                .append(URLEncoder.encode(twitterProperties.getAppSecret(), charset));
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    @EventListener({ ContextRefreshedEvent.class })
    public void configure() {
        Path workingDirPath = Paths.get(applicationProperties.getWorkingDirectory());
        if (Files.notExists(workingDirPath)) {
            try {
                Files.createDirectories(workingDirPath);
            }
            catch (IOException ex) {
                throw new RuntimeException("Unable to create working directory.");
            }
        }
    }
}
