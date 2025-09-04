package goormthonuniv.team_22_be.questionanswer.application;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.member.infrastructure.MemberRepository;
import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.domain.model.Answer;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import goormthonuniv.team_22_be.questionanswer.domain.service.AnswerService;
import goormthonuniv.team_22_be.questionanswer.infrastructure.AnswerRepository;
import goormthonuniv.team_22_be.questioncard.infrastructure.QuestionCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final QuestionCardRepository questionCardRepository;

    @Transactional
    @Override
    public Long createAnswer(Long memberId, Long questionCardId, CreateAnswerRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        QuestionCard questionCard = questionCardRepository.findById(questionCardId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_CARD_NOT_FOUND));

        if (answerRepository.existsByMemberAndQuestionCardId(member, questionCard)) {
            throw new CustomException(ErrorCode.ANSWER_ALREADY_EXISTS);
        }

        Answer answer = Answer.create(member, questionCard, request.content());

        return answerRepository.save(answer).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Long getDailyProgress(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();

        return answerRepository.countAnswersByMemberForToday(member, startOfDay, endOfDay);
    }
}
