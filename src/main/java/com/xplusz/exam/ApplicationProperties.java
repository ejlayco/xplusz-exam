package com.xplusz.exam;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app")
public class ApplicationProperties {

    private String workingDirectory;
}
