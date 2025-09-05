package goormthonuniv.team_22_be.notification.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPushSubscription is a Querydsl query type for PushSubscription
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPushSubscription extends EntityPathBase<PushSubscription> {

    private static final long serialVersionUID = 65565022L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPushSubscription pushSubscription = new QPushSubscription("pushSubscription");

    public final goormthonuniv.team_22_be.common.utils.QBaseTimeEntity _super = new goormthonuniv.team_22_be.common.utils.QBaseTimeEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath auth = createString("auth");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath endpoint = createString("endpoint");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final goormthonuniv.team_22_be.member.domain.model.QMember member;

    public final StringPath p256dh = createString("p256dh");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPushSubscription(String variable) {
        this(PushSubscription.class, forVariable(variable), INITS);
    }

    public QPushSubscription(Path<? extends PushSubscription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPushSubscription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPushSubscription(PathMetadata metadata, PathInits inits) {
        this(PushSubscription.class, metadata, inits);
    }

    public QPushSubscription(Class<? extends PushSubscription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new goormthonuniv.team_22_be.member.domain.model.QMember(forProperty("member")) : null;
    }

}

