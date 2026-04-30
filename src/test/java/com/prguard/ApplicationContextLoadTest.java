package com.prguard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "APP_BASE_URL=http://localhost:8080",
        "SPRING_DATASOURCE_URL=jdbc:tc:postgresql:16-alpine:///testdb",
        "SPRING_DATASOURCE_USERNAME=test",
        "SPRING_DATASOURCE_PASSWORD=test",
        "PRGUARD_GITHUB_WEBHOOK_SECRET=test-webhook-secret"
})
class ApplicationContextLoadTest { @Test void contextLoads() {} }
