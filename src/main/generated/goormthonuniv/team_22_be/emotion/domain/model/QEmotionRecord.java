package goormthonuniv.team_22_be.emotion.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmotionRecord is a Querydsl query type for EmotionRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmotionRecord extends EntityPathBase<EmotionRecord> {

    private static final long serialVersionUID = -730649653L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmotionRecord emotionRecord = new QEmotionRecord("emotionRecord");

    public final goormthonuniv.team_22_be.common.utils.QBaseTimeEntity _super = new goormthonuniv.team_22_be.common.utils.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<EmotionState> emotionState = createEnum("emotionState", EmotionState.class);

    public final StringPath emotionText = createString("emotionText");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final goormthonuniv.team_22_be.member.domain.model.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEmotionRecord(String variable) {
        this(EmotionRecord.class, forVariable(variable), INITS);
    }

    public QEmotionRecord(Path<? extends EmotionRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmotionRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmotionRecord(PathMetadata metadata, PathInits inits) {
        this(EmotionRecord.class, metadata, inits);
    }

    public QEmotionRecord(Class<? extends EmotionRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new goormthonuniv.team_22_be.member.domain.model.QMember(forProperty("member")) : null;
    }

}

