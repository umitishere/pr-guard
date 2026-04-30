package com.prguard.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdConfigurationValidationTest {
    @Test
    void failsWhenProdMissingWebhookSecret() {
        assertThrows(Exception.class, () -> new SpringApplicationBuilder(com.prguard.PrGuardApplication.class)
                .profiles("prod")
                .properties("APP_BASE_URL=http://localhost","SPRING_DATASOURCE_URL=jdbc:h2:mem:t","SPRING_DATASOURCE_USERNAME=sa","SPRING_DATASOURCE_PASSWORD=pw")
                .run().close());
    }
}
