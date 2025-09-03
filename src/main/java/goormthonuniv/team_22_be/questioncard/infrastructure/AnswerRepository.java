package goormthonuniv.team_22_be.questioncard.infrastructure;

import goormthonuniv.team_22_be.questioncard.domain.model.Answer;
import goormthonuniv.team_22_be.questioncard.domain.repository.AnswerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {
}
