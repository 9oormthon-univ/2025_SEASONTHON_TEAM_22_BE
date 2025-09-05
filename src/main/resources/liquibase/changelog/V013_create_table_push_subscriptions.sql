-- liquibase formatted sql

-- changeset sanghyun:13 runInTransaction:false
CREATE TABLE IF NOT EXISTS push_subscriptions (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '푸시 구독 PK',
    member_id   BIGINT                              NOT NULL COMMENT '회원 FK',
    endpoint    VARCHAR(500)                        NOT NULL COMMENT '브라우저 푸시 엔드포인트',
    p256dh      VARCHAR(255)                        NOT NULL COMMENT '키(p256dh)',
    auth        VARCHAR(255)                        NOT NULL COMMENT '키(auth)',
    active      BOOLEAN DEFAULT TRUE                NOT NULL COMMENT '활성 구독 여부',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '생성일',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',

    CONSTRAINT fk_push_subscriptions_member
    FOREIGN KEY (member_id) REFERENCES members (id)
    );

-- 고유/조회 인덱스
CREATE UNIQUE INDEX uk_push_subscriptions_endpoint ON push_subscriptions(endpoint);
CREATE INDEX idx_push_subscriptions_member_id ON push_subscriptions(member_id);