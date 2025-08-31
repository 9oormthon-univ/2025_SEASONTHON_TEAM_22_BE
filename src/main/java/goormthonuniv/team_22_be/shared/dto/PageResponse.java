package goormthonuniv.team_22_be.shared.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 제네릭 페이징 응답을 위한 Record DTO
 * @param content      페이지의 데이터 목록
 * @param pageNumber   현재 페이지 번호 (0부터 시작)
 * @param pageSize     페이지 크기
 * @param totalElements 전체 요소의 수
 * @param totalPages   전체 페이지 수
 * @param first        첫 페이지 여부
 * @param last         마지막 페이지 여부
 * @param <T>          content의 데이터 타입
 */
public record PageResponse<T>(

        @NotNull(message = "Content는 null일 수 없습니다.")
        List<T> content,

        @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
        int pageNumber,

        @Min(value = 0, message = "페이지 크기는 0 이상이어야 합니다.")
        int pageSize,

        @Min(value = 0, message = "전체 요소 수는 0 이상이어야 합니다.")
        long totalElements,

        @Min(value = 0, message = "전체 페이지 수는 0 이상이어야 합니다.")
        int totalPages,

        boolean first,
        boolean last
) {

    public static <S, T> PageResponse<T> of(Page<S> page, Function<S, T> converter) {
        Objects.requireNonNull(page, "Page 객체는 null일 수 없습니다.");
        Objects.requireNonNull(converter, "Converter 함수는 null일 수 없습니다.");

        List<T> content = page.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        Objects.requireNonNull(page, "Page 객체는 null일 수 없습니다.");
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public static <T> PageResponse<T> empty() {
        return new PageResponse<>(Collections.emptyList(), 0, 0, 0, 1, true, true);
    }
}

