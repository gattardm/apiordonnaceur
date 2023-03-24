package org.medhead.emergencysystem.apiordonnanceur;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "org.medhead.emergencysystem.apiordonnanceur")
public class CustomProperties {

    private String apiUrlIncidents;
    private String apiUrlOperators;

}
