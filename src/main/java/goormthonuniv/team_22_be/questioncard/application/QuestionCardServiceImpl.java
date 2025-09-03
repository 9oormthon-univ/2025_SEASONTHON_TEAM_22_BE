package goormthonuniv.team_22_be.questioncard.application;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import goormthonuniv.team_22_be.questioncard.domain.service.QuestionCardService;
import goormthonuniv.team_22_be.questioncard.infrastructure.QuestionCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionCardServiceImpl implements QuestionCardService {

    private final QuestionCardRepository questionCardRepository;

    @Transactional(readOnly = true)
    @Override
    public QuestionCard getNextCard(Long currentId) {
        return questionCardRepository.findNextCard(currentId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_CARD_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public QuestionCard getPreviousCard(Long currentId) {
        return questionCardRepository.findPreviousCard(currentId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_CARD_NOT_FOUND));
    }
}
