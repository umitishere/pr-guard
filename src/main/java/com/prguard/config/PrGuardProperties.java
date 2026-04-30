package com.prguard.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "prguard")
@Validated
public record PrGuardProperties(
        @NotBlank(message = "APP_BASE_URL is required") String appBaseUrl,
        Github github
) {
    public record Github(
            Webhook webhook,
            App app
    ) {}

    public record Webhook(
            String secret,
            boolean requireSignature
    ) {}

    public record App(
            String id,
            String slug,
            String privateKeyBase64,
            String apiBaseUrl,
            String webBaseUrl
    ) {}
}
