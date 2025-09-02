package goormthonuniv.team_22_be.api.activity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivityLike is a Querydsl query type for ActivityLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivityLike extends EntityPathBase<ActivityLike> {

    private static final long serialVersionUID = -1299657631L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivityLike activityLike = new QActivityLike("activityLike");

    public final goormthonuniv.team_22_be.common.utils.QBaseTimeEntity _super = new goormthonuniv.team_22_be.common.utils.QBaseTimeEntity(this);

    public final QActivity activity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final goormthonuniv.team_22_be.member.domain.model.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QActivityLike(String variable) {
        this(ActivityLike.class, forVariable(variable), INITS);
    }

    public QActivityLike(Path<? extends ActivityLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActivityLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActivityLike(PathMetadata metadata, PathInits inits) {
        this(ActivityLike.class, metadata, inits);
    }

    public QActivityLike(Class<? extends ActivityLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.activity = inits.isInitialized("activity") ? new QActivity(forProperty("activity")) : null;
        this.member = inits.isInitialized("member") ? new goormthonuniv.team_22_be.member.domain.model.QMember(forProperty("member")) : null;
    }

}

