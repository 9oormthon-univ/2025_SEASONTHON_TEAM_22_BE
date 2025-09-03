package goormthonuniv.team_22_be.comment.domain.service;

import goormthonuniv.team_22_be.comment.application.CommentService;
import goormthonuniv.team_22_be.comment.application.dto.CommentCreateDto;
import goormthonuniv.team_22_be.comment.application.dto.CommentResponseDto;
import goormthonuniv.team_22_be.comment.application.dto.CommentUpdateDto;
import goormthonuniv.team_22_be.comment.domain.model.Comment;
import goormthonuniv.team_22_be.comment.domain.repository.CommentRepository;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.post.domain.model.Post;
import goormthonuniv.team_22_be.post.domain.repository.PostRepository;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CommentResponseDto> listByPost(Long postId, Pageable pageable) {
        var page = commentRepository.findByPost_Id(postId, pageable)
                .map(CommentResponseDto::from);
        return PageResponse.of(page);
    }

    @Override
    public CommentResponseDto create(Long postId, CommentCreateDto dto) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다. id=" + postId));

        Comment comment = Comment.create(
                Member.builder().id(memberId).build(),
                post,
                dto.content()
        );

        return CommentResponseDto.from(commentRepository.save(comment));
    }

    @Override
    public CommentResponseDto update(Long commentId, CommentUpdateDto dto) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "댓글을 찾을 수 없습니다. id=" + commentId));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN, "자신의 댓글만 수정할 수 있습니다.");
        }

        comment.update(dto.content());
        return CommentResponseDto.from(comment);
    }

    @Override
    public void delete(Long commentId) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "댓글을 찾을 수 없습니다. id=" + commentId));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN, "자신의 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}