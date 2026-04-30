package com.prguard.github.webhook.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "webhook_event")
public class WebhookEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String deliveryId;
    @Column(nullable = false)
    private String eventType;
    private String action;
    private Long installationId;
    private Long repositoryId;
    private String repositoryFullName;
    private Integer pullRequestNumber;
    @Column(nullable = false)
    private String payloadSha256;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WebhookStatus status;
    @Column(nullable = false)
    private OffsetDateTime receivedAt;
    private OffsetDateTime processedAt;
    private String errorMessage;
    // getters/setters
    public Long getId() { return id; }
    public String getDeliveryId() { return deliveryId; }
    public void setDeliveryId(String deliveryId) { this.deliveryId = deliveryId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Long getInstallationId() { return installationId; }
    public void setInstallationId(Long installationId) { this.installationId = installationId; }
    public Long getRepositoryId() { return repositoryId; }
    public void setRepositoryId(Long repositoryId) { this.repositoryId = repositoryId; }
    public String getRepositoryFullName() { return repositoryFullName; }
    public void setRepositoryFullName(String repositoryFullName) { this.repositoryFullName = repositoryFullName; }
    public Integer getPullRequestNumber() { return pullRequestNumber; }
    public void setPullRequestNumber(Integer pullRequestNumber) { this.pullRequestNumber = pullRequestNumber; }
    public String getPayloadSha256() { return payloadSha256; }
    public void setPayloadSha256(String payloadSha256) { this.payloadSha256 = payloadSha256; }
    public WebhookStatus getStatus() { return status; }
    public void setStatus(WebhookStatus status) { this.status = status; }
    public OffsetDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(OffsetDateTime receivedAt) { this.receivedAt = receivedAt; }
    public OffsetDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(OffsetDateTime processedAt) { this.processedAt = processedAt; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
