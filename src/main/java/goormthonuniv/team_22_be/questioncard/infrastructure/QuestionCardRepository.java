package goormthonuniv.team_22_be.questioncard.infrastructure;

import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import goormthonuniv.team_22_be.questioncard.domain.repository.QuestionCardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCardRepository extends JpaRepository<QuestionCard, Long>, QuestionCardRepositoryCustom {

}
