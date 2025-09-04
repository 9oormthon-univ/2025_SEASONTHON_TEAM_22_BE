-- liquibase formatted sql

-- changeset sanghyun:V012 runInTransaction:false

CREATE TABLE IF NOT EXISTS push_subscriptions (
                                                  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                  member_id    BIGINT      NOT NULL,
                                                  endpoint     VARCHAR(500) NOT NULL,
    p256dh       VARCHAR(255) NOT NULL,
    auth         VARCHAR(255) NOT NULL,
    created_at   DATETIME     NOT NULL,
    updated_at   DATETIME     NOT NULL,
    CONSTRAINT fk_push_sub_member FOREIGN KEY (member_id) REFERENCES members(id),
    CONSTRAINT uk_push_endpoint UNIQUE (endpoint)
    );