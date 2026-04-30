package com.prguard.repository;

import com.prguard.github.webhook.model.WebhookEvent;
import com.prguard.github.webhook.model.WebhookStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DataJpaTest
@TestPropertySource(properties = {"APP_BASE_URL=http://localhost:8080", "PRGUARD_GITHUB_WEBHOOK_SECRET=test-webhook-secret"})
class WebhookEventRepositoryIntegrationTest {
    @Container static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:16-alpine");
    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry r){r.add("spring.datasource.url", pg::getJdbcUrl);r.add("spring.datasource.username", pg::getUsername);r.add("spring.datasource.password", pg::getPassword);}    
    @Autowired WebhookEventRepository repo;
    @Test void persistsMetadataOnly() {
        WebhookEvent e = new WebhookEvent(); e.setDeliveryId("abc"); e.setEventType("pull_request"); e.setPayloadSha256("x"); e.setStatus(WebhookStatus.RECEIVED); e.setReceivedAt(OffsetDateTime.now());
        repo.save(e);
        assertTrue(repo.findByDeliveryId("abc").isPresent());
    }
}
