package com.prguard.github.webhook.security;

import com.prguard.config.PrGuardProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebhookSignatureVerifierTest {
    @Test
    void verifiesValidSignature() {
        PrGuardProperties props = new PrGuardProperties("http://localhost", new PrGuardProperties.Github(new PrGuardProperties.Webhook("test-webhook-secret", true), new PrGuardProperties.App(null,null,null,null,null)));
        WebhookSignatureVerifier verifier = new WebhookSignatureVerifier(props);
        byte[] payload = "{}".getBytes();
        String sig = "sha256=697d4d88a13b03d16b218b4f87f68f460ff7bbf5ad47e15f95ed59fba27ca2a9";
        assertTrue(verifier.verify(payload, sig));
        assertFalse(verifier.verify(payload, "sha256=bad"));
    }
}
