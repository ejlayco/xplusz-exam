package com.xplusz.exam;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@RestController("/search")
public class SearchRestController {

    private final Twitter twitter;

    private final ConnectionRepository connectionRepository;

    private final ApplicationProperties applicationProperties;

    @Inject
    public SearchRestController(Twitter twitter, ConnectionRepository connectionRepository, ApplicationProperties applicationProperties) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
        this.applicationProperties = applicationProperties;
    }

    public List<Tweet> search() {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return Collections.emptyList();
        }
        String credentials = getCredentials();
        twitter.searchOperations();
        return null;
    }

    private String getCredentials() {
        Path credentialsPath = Paths.get(applicationProperties.getWorkingDirectory(), "credentials");
        try {
            return Files.readAllLines(credentialsPath).get(0);
        }
        catch (IOException e) {
            return "";
        }
    }
}
