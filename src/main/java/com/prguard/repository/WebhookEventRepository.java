package com.prguard.repository;

import com.prguard.github.webhook.model.WebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebhookEventRepository extends JpaRepository<WebhookEvent, Long> {
    Optional<WebhookEvent> findByDeliveryId(String deliveryId);
}
