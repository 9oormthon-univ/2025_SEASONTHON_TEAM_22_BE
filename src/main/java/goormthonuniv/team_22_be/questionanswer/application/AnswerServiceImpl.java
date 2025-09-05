package goormthonuniv.team_22_be.questionanswer.application;

import com.querydsl.core.Tuple;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.member.domain.repository.MemberRepository;
import goormthonuniv.team_22_be.questionanswer.application.dto.AnswerResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyAnswerRecordResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.ProgressStatusResponse;
import goormthonuniv.team_22_be.questionanswer.domain.model.Answer;
import goormthonuniv.team_22_be.questionanswer.domain.service.AnswerService;
import goormthonuniv.team_22_be.questionanswer.infrastructure.AnswerRepository;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import goormthonuniv.team_22_be.questioncard.infrastructure.QuestionCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private static final int DAILY_GOAL = 6;

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

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();

        if (answerRepository.existsByMemberAndQuestionCardId(member, questionCard, startOfDay)) {
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

    @Transactional(readOnly = true)
    @Override
    public ProgressStatusResponse getProgressStatus(Long memberId) {
        List<Long> dailyCounts = answerRepository.getDailyAnswerCounts(memberId);

        Long completedAnswers = answerRepository.getCompletedAnswers(memberId);

        Long totalTrainedSessions = (long) dailyCounts.size();

        Long averageCompletion = calculateAverageCompletion(dailyCounts);

        return ProgressStatusResponse.from(totalTrainedSessions, completedAnswers, averageCompletion);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DailyAnswerRecordResponse> getDailyAnswerRecords(Long memberId, Pageable pageable) {
        Page<Tuple> tuples = answerRepository.findDailyAnswerRecords(memberId, pageable);

        List<DailyAnswerRecordResponse> records = tuples.stream()
                .map(tuple -> {
                    LocalDate date = toLocalDate(tuple.get(0, Object.class));
                    long answeredCount = Optional.ofNullable(tuple.get(1, Number.class))
                            .map(Number::longValue)
                            .orElse(0L);
                    int completionRate = (int) Math.round(answeredCount * 100.0 / DAILY_GOAL);
                    return new DailyAnswerRecordResponse(date, answeredCount, completionRate);
                })
                .toList();

        return new PageImpl<>(records, pageable, tuples.getTotalElements());
    }

    @Override
    public List<AnswerResponse> getAnswersByDate(Long memberId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        List<Answer> answers = answerRepository.findAnswersByDate(memberId, startOfDay, endOfDay);

        return answers.stream()
                .map(answer -> new AnswerResponse(
                        answer.getQuestionCard().getCardType().getDescription(),
                        answer.getQuestionCard().getContent(),
                        answer.getContent())
                )
                .toList();
    }

    private Long calculateAverageCompletion(List<Long> dailyCounts) {
        if (dailyCounts == null || dailyCounts.isEmpty()) {
            return 0L;
        }

        double average = dailyCounts.stream()
                .mapToDouble(count -> Math.min(100.0, (double) count / DAILY_GOAL * 100))
                .average()
                .orElse(0.0);

        return Math.round(average);
    }

    private LocalDate toLocalDate(Object rawDate) {
        if (rawDate instanceof LocalDate ld) return ld;
        if (rawDate instanceof LocalDateTime ldt) return ldt.toLocalDate();
        if (rawDate instanceof java.sql.Date sqlDate) return sqlDate.toLocalDate();
        throw new IllegalStateException("Unexpected date type: " + rawDate.getClass());
    }
}
