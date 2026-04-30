package com.prguard.github.webhook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "APP_BASE_URL=http://localhost:8080",
        "SPRING_DATASOURCE_URL=jdbc:tc:postgresql:16-alpine:///testdb",
        "SPRING_DATASOURCE_USERNAME=test",
        "SPRING_DATASOURCE_PASSWORD=test",
        "PRGUARD_GITHUB_WEBHOOK_SECRET=test-webhook-secret",
        "PRGUARD_REQUIRE_WEBHOOK_SIGNATURE=true"
})
class GithubWebhookControllerTest {
    @Autowired MockMvc mockMvc;
    @Test
    void returnsBadRequestWhenRequiredHeadersMissing() throws Exception {
        mockMvc.perform(post("/api/github/webhook").content("{}")).andExpect(status().isBadRequest());
    }
    @Test
    void returnsUnauthorizedForBadSignature() throws Exception {
        mockMvc.perform(post("/api/github/webhook").header("X-GitHub-Event", "pull_request").header("X-GitHub-Delivery", "d1").header("X-Hub-Signature-256", "sha256=bad").content("{}"))
                .andExpect(status().isUnauthorized());
    }
}
