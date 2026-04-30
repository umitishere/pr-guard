package com.prguard.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Component
public class ProdConfigurationValidator implements ApplicationRunner {
    private final PrGuardProperties properties;
    private final Environment environment;

    public ProdConfigurationValidator(PrGuardProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            return;
        }
        if (!StringUtils.hasText(properties.github().webhook().secret())) {
            throw new IllegalStateException("PRGUARD_GITHUB_WEBHOOK_SECRET must be set in prod profile");
        }
        if (!properties.github().webhook().requireSignature()) {
            throw new IllegalStateException("PRGUARD_REQUIRE_WEBHOOK_SIGNATURE must not be false in prod profile");
        }
    }
}
