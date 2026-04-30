package com.prguard.github.webhook.handler;

import com.prguard.github.webhook.model.WebhookEvent;

public interface WebhookHandler {
    boolean supports(String eventType);
    void handle(WebhookEvent event);
}
