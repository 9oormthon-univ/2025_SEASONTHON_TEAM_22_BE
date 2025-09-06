package goormthonuniv.team_22_be.comment.application;

import goormthonuniv.team_22_be.comment.application.dto.CreateCommentRequest;
import goormthonuniv.team_22_be.comment.application.dto.CommentResponseDto;
import goormthonuniv.team_22_be.comment.application.dto.UpdateCommentRequest;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    PageResponse<CommentResponseDto> getCommentsPage(Long postId, Pageable pageable);

    PageResponse<CommentResponseDto> getMyComments(Long memberId, Pageable pageable);

    CommentResponseDto createComment(Long memberId, Long postId, CreateCommentRequest dto);

    CommentResponseDto updateComment(Long memberId, Long commentId, UpdateCommentRequest dto);

    void deleteComment(Long memberId, Long commentId);
}
