package com.prguard.github.webhook.handler;

import com.prguard.github.webhook.model.WebhookEvent;
import com.prguard.github.webhook.model.WebhookStatus;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class PullRequestWebhookHandler implements WebhookHandler {
    public boolean supports(String eventType) { return "pull_request".equals(eventType); }
    public void handle(WebhookEvent event) { event.setStatus(WebhookStatus.PROCESSED); event.setProcessedAt(OffsetDateTime.now()); }
}
