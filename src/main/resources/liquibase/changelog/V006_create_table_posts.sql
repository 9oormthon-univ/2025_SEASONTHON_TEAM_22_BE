-- liquibase formatted sql

-- changeset Seungwon-Choi:6 runInTransaction:false

CREATE TABLE IF NOT EXISTS posts (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '게시물 PK',
    member_id      bigint                              NOT NULL COMMENT '회원 FK',
    activity_id    bigint                              NOT NULL COMMENT '활동 FK',
    category       varchar(20)                         NOT NULL COMMENT '게시물 카테고리',
    likes          bigint    DEFAULT 0                 NOT NULL COMMENT '좋아요 수',
    rating         bigint    DEFAULT 0                 NOT NULL COMMENT '별점',
    title          varchar(200)                        NOT NULL COMMENT '게시물 제목',
    content        varchar(255)                        NOT NULL COMMENT '게시물 내용',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT fk_posts_to_members
        FOREIGN KEY (member_id) REFERENCES members (id),

    CONSTRAINT fk_posts_to_activities
        FOREIGN KEY (activity_id) REFERENCES activities (id),

    CONSTRAINT chk_category
        CHECK (category IN ('POST', 'REVIEW'))
);

CREATE INDEX idx_posts_member_id ON posts(member_id);
CREATE INDEX idx_posts_category ON posts(category);
