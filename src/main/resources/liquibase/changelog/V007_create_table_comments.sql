-- liquibase formatted sql

-- changeset Seungwon-Choi:7 runInTransaction:false

CREATE TABLE IF NOT EXISTS comments (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '댓글 PK',
    post_id       bigint                              NOT NULL COMMENT '게시물 FK',
    account_id    varchar(200)                        NOT NULL COMMENT '회원 account ID',
    content       varchar(255)                        NOT NULL COMMENT '댓글 내용',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT fk_comments_to_posts
        FOREIGN KEY (post_id) REFERENCES posts (id)
);

CREATE INDEX idx_comments_post_id ON comments(post_id);
