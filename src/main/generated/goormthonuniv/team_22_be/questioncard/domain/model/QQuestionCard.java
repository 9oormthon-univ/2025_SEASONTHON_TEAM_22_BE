package goormthonuniv.team_22_be.questioncard.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestionCard is a Querydsl query type for QuestionCard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionCard extends EntityPathBase<QuestionCard> {

    private static final long serialVersionUID = 1673695976L;

    public static final QQuestionCard questionCard = new QQuestionCard("questionCard");

    public final goormthonuniv.team_22_be.common.utils.QBaseTimeEntity _super = new goormthonuniv.team_22_be.common.utils.QBaseTimeEntity(this);

    public final EnumPath<CardType> cardType = createEnum("cardType", CardType.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QQuestionCard(String variable) {
        super(QuestionCard.class, forVariable(variable));
    }

    public QQuestionCard(Path<? extends QuestionCard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionCard(PathMetadata metadata) {
        super(QuestionCard.class, metadata);
    }

}

