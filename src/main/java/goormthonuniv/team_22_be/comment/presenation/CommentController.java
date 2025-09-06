package goormthonuniv.team_22_be.comment.presenation;

import goormthonuniv.team_22_be.comment.application.CommentService;
import goormthonuniv.team_22_be.comment.application.dto.CreateCommentRequest;
import goormthonuniv.team_22_be.comment.application.dto.CommentResponseDto;
import goormthonuniv.team_22_be.comment.application.dto.UpdateCommentRequest;
import goormthonuniv.team_22_be.comment.presenation.docs.CommentApiDocs;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController implements CommentApiDocs {

    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResult<PageResponse<CommentResponseDto>>> getCommentsPage(@PathVariable Long postId, @PageableDefault Pageable pageable) {
        var page = commentService.getCommentsPage(postId, pageable);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @GetMapping("/{memberId}")
    @Override
    public ResponseEntity<ApiResult<PageResponse<CommentResponseDto>>> getMyComments(@PathVariable Long memberId, Pageable pageable) {
        var page = commentService.getMyComments(memberId, pageable);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @PostMapping("/{memberId}/{postId}")
    @Override
    public ResponseEntity<ApiResult<CommentResponseDto>> createComment(@PathVariable Long memberId, @PathVariable Long postId, @RequestBody CreateCommentRequest request) {
        var res = commentService.createComment(memberId, postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(res));
    }

    @PutMapping("/{memberId}/{commentId}")
    public ResponseEntity<ApiResult<CommentResponseDto>> updateComment(@PathVariable Long memberId, @PathVariable Long commentId, @RequestBody UpdateCommentRequest request) {
        var res = commentService.updateComment(memberId, commentId, request);
        return ResponseEntity.ok(ApiResult.ok(res));
    }

    @DeleteMapping("/{memberId}/{commentId}")
    @Override
    public ResponseEntity<ApiResult<Void>> deleteComment(@PathVariable Long memberId, @PathVariable Long commentId) {
        commentService.deleteComment(memberId, commentId);
        return ResponseEntity.ok(ApiResult.ok(null));
    }
}