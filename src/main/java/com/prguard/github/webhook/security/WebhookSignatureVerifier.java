package com.prguard.github.webhook.security;

import com.prguard.config.PrGuardProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class WebhookSignatureVerifier {
    private final PrGuardProperties properties;

    public WebhookSignatureVerifier(PrGuardProperties properties) {
        this.properties = properties;
    }

    public boolean verify(byte[] payload, String signatureHeader) {
        if (!properties.github().webhook().requireSignature()) return true;
        if (signatureHeader == null || !signatureHeader.startsWith("sha256=")) return false;
        String expected = "sha256=" + hmacSha256(payload, properties.github().webhook().secret());
        return MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8), signatureHeader.getBytes(StandardCharsets.UTF_8));
    }

    private String hmacSha256(byte[] payload, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hash = mac.doFinal(payload);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to verify signature", e);
        }
    }
}
