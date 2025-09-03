package goormthonuniv.team_22_be.comment.application;

import goormthonuniv.team_22_be.comment.application.dto.CommentCreateDto;
import goormthonuniv.team_22_be.comment.application.dto.CommentResponseDto;
import goormthonuniv.team_22_be.comment.application.dto.CommentUpdateDto;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    PageResponse<CommentResponseDto> listByPost(Long postId, Pageable pageable);
    PageResponse<CommentResponseDto> myComments(Pageable pageable);
    CommentResponseDto create(Long postId, CommentCreateDto dto);
    CommentResponseDto update(Long commentId, CommentUpdateDto dto);
    void delete(Long commentId);
}
