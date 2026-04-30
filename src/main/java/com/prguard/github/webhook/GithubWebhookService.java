package com.prguard.github.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prguard.github.webhook.handler.WebhookHandler;
import com.prguard.github.webhook.model.WebhookEvent;
import com.prguard.github.webhook.model.WebhookStatus;
import com.prguard.repository.WebhookEventRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.List;

@Service
public class GithubWebhookService {
    private final WebhookEventRepository repository;
    private final List<WebhookHandler> handlers;
    private final ObjectMapper objectMapper;

    public GithubWebhookService(WebhookEventRepository repository, List<WebhookHandler> handlers, ObjectMapper objectMapper) {
        this.repository = repository; this.handlers = handlers; this.objectMapper = objectMapper;
    }

    public WebhookEvent process(String deliveryId, String eventType, byte[] payload) {
        WebhookEvent event = buildMetadata(deliveryId, eventType, payload);
        WebhookHandler handler = handlers.stream().filter(h -> h.supports(eventType)).findFirst().orElse(null);
        if (handler == null) {
            event.setStatus(WebhookStatus.IGNORED);
            event.setProcessedAt(OffsetDateTime.now());
            return repository.save(event);
        }
        try {
            handler.handle(event);
        } catch (Exception ex) {
            event.setStatus(WebhookStatus.FAILED);
            event.setProcessedAt(OffsetDateTime.now());
            event.setErrorMessage(sanitizeError(ex.getMessage()));
        }
        return repository.save(event);
    }
    private WebhookEvent buildMetadata(String deliveryId, String eventType, byte[] payload) {
        try {
            JsonNode root = objectMapper.readTree(payload);
            WebhookEvent e = new WebhookEvent();
            e.setDeliveryId(deliveryId); e.setEventType(eventType); e.setStatus(WebhookStatus.RECEIVED); e.setReceivedAt(OffsetDateTime.now());
            e.setAction(text(root, "action"));
            e.setInstallationId(num(root.path("installation").path("id")));
            e.setRepositoryId(num(root.path("repository").path("id")));
            e.setRepositoryFullName(text(root.path("repository"), "full_name"));
            if (root.path("pull_request").path("number").isInt()) e.setPullRequestNumber(root.path("pull_request").path("number").asInt());
            e.setPayloadSha256(sha256(payload));
            return e;
        } catch (Exception e) { throw new IllegalArgumentException("Invalid webhook JSON"); }
    }
    private String text(JsonNode node, String field) { JsonNode value = node.path(field); return value.isMissingNode()||value.isNull()?null:value.asText(); }
    private Long num(JsonNode node) { return node.isNumber()?node.asLong():null; }
    private String sha256(byte[] payload) { try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(payload)); } catch(Exception e){ throw new IllegalStateException(e);} }
    private String sanitizeError(String msg) { if (msg == null) return "processing error"; return msg.substring(0, Math.min(200, msg.length())); }
}
