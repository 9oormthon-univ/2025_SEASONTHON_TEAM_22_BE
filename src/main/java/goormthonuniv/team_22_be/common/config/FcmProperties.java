package goormthonuniv.team_22_be.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fcm")
public record FcmProperties(
        String filePath,
        String url,
        String googleApi,  // google-api
        String projectId
) {}