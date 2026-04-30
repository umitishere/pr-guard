package com.prguard.github.webhook.security;

import com.prguard.config.PrGuardProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebhookSignatureVerifierTest {
    private String hmacSha256(byte[] payload, String secret) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hash = mac.doFinal(payload);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void verifiesValidSignature() {
        PrGuardProperties props = new PrGuardProperties("http://localhost", new PrGuardProperties.Github(new PrGuardProperties.Webhook("test-webhook-secret", true), new PrGuardProperties.App(null,null,null,null,null)));
        WebhookSignatureVerifier verifier = new WebhookSignatureVerifier(props);
        byte[] payload = "{}".getBytes();
        String sig = "sha256=" + hmacSha256(payload, "test-webhook-secret");
        assertTrue(verifier.verify(payload, sig));
        assertFalse(verifier.verify(payload, "sha256=bad"));
    }
}
