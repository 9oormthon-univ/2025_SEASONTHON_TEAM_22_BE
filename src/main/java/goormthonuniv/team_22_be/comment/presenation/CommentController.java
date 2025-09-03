package goormthonuniv.team_22_be.comment.presenation;

import goormthonuniv.team_22_be.comment.application.CommentService;
import goormthonuniv.team_22_be.comment.application.dto.CommentCreateDto;
import goormthonuniv.team_22_be.comment.application.dto.CommentResponseDto;
import goormthonuniv.team_22_be.comment.application.dto.CommentUpdateDto;
import goormthonuniv.team_22_be.comment.presenation.docs.CommentApiDocs;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApiDocs {

    private final CommentService commentService;

    @Override
    public ResponseEntity<ApiResult<PageResponse<CommentResponseDto>>> list(Long postId, Pageable pageable) {
        var page = commentService.listByPost(postId, pageable);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @Override
    public ResponseEntity<ApiResult<CommentResponseDto>> create(Long postId, CommentCreateDto dto) {
        var res = commentService.create(postId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(res));
    }

    @Override
    public ResponseEntity<ApiResult<CommentResponseDto>> update(Long id, CommentUpdateDto dto) {
        var res = commentService.update(id, dto);
        return ResponseEntity.ok(ApiResult.ok(res));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> delete(Long id) {
        commentService.delete(id);
        return ResponseEntity.ok(ApiResult.ok(null));
    }
}