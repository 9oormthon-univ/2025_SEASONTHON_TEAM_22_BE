package goormthonuniv.team_22_be.post.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLike is a Querydsl query type for PostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLike extends EntityPathBase<PostLike> {

    private static final long serialVersionUID = -2142059437L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLike postLike = new QPostLike("postLike");

    public final goormthonuniv.team_22_be.common.utils.QBaseTimeEntity _super = new goormthonuniv.team_22_be.common.utils.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final goormthonuniv.team_22_be.member.domain.model.QMember member;

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPostLike(String variable) {
        this(PostLike.class, forVariable(variable), INITS);
    }

    public QPostLike(Path<? extends PostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLike(PathMetadata metadata, PathInits inits) {
        this(PostLike.class, metadata, inits);
    }

    public QPostLike(Class<? extends PostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new goormthonuniv.team_22_be.member.domain.model.QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

