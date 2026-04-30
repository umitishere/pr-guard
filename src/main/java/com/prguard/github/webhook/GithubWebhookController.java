package com.prguard.github.webhook;

import com.prguard.github.webhook.security.WebhookSignatureVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/github/webhook")
public class GithubWebhookController {
    private final WebhookSignatureVerifier verifier;
    private final GithubWebhookService service;

    public GithubWebhookController(WebhookSignatureVerifier verifier, GithubWebhookService service) {
        this.verifier = verifier; this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> receive(@RequestHeader(value = "X-GitHub-Event", required = false) String eventType,
                                        @RequestHeader(value = "X-GitHub-Delivery", required = false) String deliveryId,
                                        @RequestHeader(value = "X-Hub-Signature-256", required = false) String signature,
                                        @RequestBody byte[] payload) {
        if (eventType == null || eventType.isBlank() || deliveryId == null || deliveryId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (!verifier.verify(payload, signature)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.process(deliveryId, eventType, payload);
        return ResponseEntity.accepted().build();
    }
}
