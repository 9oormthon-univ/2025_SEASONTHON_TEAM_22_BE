package goormthonuniv.team_22_be.activity.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivityApplication is a Querydsl query type for ActivityApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivityApplication extends EntityPathBase<ActivityApplication> {

    private static final long serialVersionUID = -376896970L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivityApplication activityApplication = new QActivityApplication("activityApplication");

    public final goormthonuniv.team_22_be.common.utils.QBaseTimeEntity _super = new goormthonuniv.team_22_be.common.utils.QBaseTimeEntity(this);

    public final QActivity activity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final goormthonuniv.team_22_be.member.domain.model.QMember member;

    public final EnumPath<ApplicationStatus> status = createEnum("status", ApplicationStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QActivityApplication(String variable) {
        this(ActivityApplication.class, forVariable(variable), INITS);
    }

    public QActivityApplication(Path<? extends ActivityApplication> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActivityApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActivityApplication(PathMetadata metadata, PathInits inits) {
        this(ActivityApplication.class, metadata, inits);
    }

    public QActivityApplication(Class<? extends ActivityApplication> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.activity = inits.isInitialized("activity") ? new QActivity(forProperty("activity")) : null;
        this.member = inits.isInitialized("member") ? new goormthonuniv.team_22_be.member.domain.model.QMember(forProperty("member")) : null;
    }

}

