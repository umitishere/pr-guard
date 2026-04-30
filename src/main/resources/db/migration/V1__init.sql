CREATE TABLE IF NOT EXISTS github_installation (
    id BIGSERIAL PRIMARY KEY,
    installation_id BIGINT UNIQUE NOT NULL,
    account_login VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS github_repository (
    id BIGSERIAL PRIMARY KEY,
    repository_id BIGINT UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    installation_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS scan (
    id BIGSERIAL PRIMARY KEY,
    repository_id BIGINT,
    pull_request_number INT,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS finding (
    id BIGSERIAL PRIMARY KEY,
    scan_id BIGINT,
    rule_id VARCHAR(255),
    message TEXT,
    severity VARCHAR(32),
    confidence VARCHAR(32),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS webhook_event (
    id BIGSERIAL PRIMARY KEY,
    delivery_id VARCHAR(255) UNIQUE NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    action VARCHAR(255),
    installation_id BIGINT,
    repository_id BIGINT,
    repository_full_name VARCHAR(255),
    pull_request_number INT,
    payload_sha256 VARCHAR(64) NOT NULL,
    status VARCHAR(32) NOT NULL,
    received_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    error_message VARCHAR(255)
);
