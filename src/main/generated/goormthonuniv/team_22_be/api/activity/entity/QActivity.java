package goormthonuniv.team_22_be.api.activity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivity is a Querydsl query type for Activity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivity extends EntityPathBase<Activity> {

    private static final long serialVersionUID = -1986556502L;

    public static final QActivity activity = new QActivity("activity");

    public final goormthonuniv.team_22_be.common.utils.QBaseTimeEntity _super = new goormthonuniv.team_22_be.common.utils.QBaseTimeEntity(this);

    public final EnumPath<ActivityType> activityType = createEnum("activityType", ActivityType.class);

    public final ListPath<ActivityApplication, QActivityApplication> applicationList = this.<ActivityApplication, QActivityApplication>createList("applicationList", ActivityApplication.class, QActivityApplication.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> applyEndAt = createDateTime("applyEndAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> applyStartAt = createDateTime("applyStartAt", java.time.LocalDateTime.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ActivityLike, QActivityLike> likeList = this.<ActivityLike, QActivityLike>createList("likeList", ActivityLike.class, QActivityLike.class, PathInits.DIRECT2);

    public final NumberPath<Long> likes = createNumber("likes", Long.class);

    public final StringPath location = createString("location");

    public final EnumPath<RecruitStatus> recruitStatus = createEnum("recruitStatus", RecruitStatus.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QActivity(String variable) {
        super(Activity.class, forVariable(variable));
    }

    public QActivity(Path<? extends Activity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivity(PathMetadata metadata) {
        super(Activity.class, metadata);
    }

}

