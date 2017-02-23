package com.xplusz.exam;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

@RestController("/login")
public class LoginRestController {

    private final ApplicationProperties applicationProperties;

    private final Twitter twitter;

    @Inject
    public LoginRestController(ApplicationProperties applicationProperties, Twitter twitter) {
        this.applicationProperties = applicationProperties;
        this.twitter = twitter;
    }

    @PostMapping
    public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
        final String credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Path passPath = Paths.get(applicationProperties.getWorkingDirectory(), "credentials");
        try {
            if (Files.notExists(passPath)) {
                Files.createFile(passPath);
            }
            Files.write(passPath, credentials.getBytes(), StandardOpenOption.WRITE);
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to write to file in working directory.");
        }
    }
}
