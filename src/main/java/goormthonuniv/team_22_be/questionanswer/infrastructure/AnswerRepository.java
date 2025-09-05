package goormthonuniv.team_22_be.questionanswer.infrastructure;

import goormthonuniv.team_22_be.questionanswer.domain.model.Answer;
import goormthonuniv.team_22_be.questionanswer.domain.repository.AnswerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {

    Optional<Answer> findAnswersByContent(String content);
}
