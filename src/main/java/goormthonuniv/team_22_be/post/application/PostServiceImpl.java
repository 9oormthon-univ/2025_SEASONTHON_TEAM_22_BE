package goormthonuniv.team_22_be.post.application;

import goormthonuniv.team_22_be.activity.domain.model.Activity;
import goormthonuniv.team_22_be.activity.domain.repository.ActivityRepository;
import goormthonuniv.team_22_be.post.application.dto.PostCreateDto;
import goormthonuniv.team_22_be.post.application.dto.PostResponseDto;
import goormthonuniv.team_22_be.post.application.dto.PostUpdateDto;
import goormthonuniv.team_22_be.post.domain.model.PostLike;
import goormthonuniv.team_22_be.post.domain.repository.PostLikeRepository;
import goormthonuniv.team_22_be.post.domain.repository.PostRepository;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.post.domain.model.Post;
import goormthonuniv.team_22_be.post.domain.model.PostCategory;
import goormthonuniv.team_22_be.post.domain.service.PostService;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ActivityRepository activityRepository;

    /**
     * 게시글 list 조회 (파라미터 없으면 전체 게시글 list return)
     * @param pageable
     * @param category
     * @param activityId
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageResponse<PostResponseDto> list(Pageable pageable, String category, Long activityId, Long memberId) {

        if(category != null && !category.isBlank()) {
            PostCategory cat;
            try {
                cat = PostCategory.valueOf(category.toUpperCase());
            }
            catch(IllegalArgumentException e) {
                throw new CustomException(ErrorCode.BAD_REQUEST, "유효하지 않은 카테고리 : " + category);
            }
            return PageResponse.of(postRepository.findByCategory(cat, pageable).map(PostResponseDto::from));
        }
        if (activityId != null) {
            return PageResponse.of(postRepository.findByActivity_Id(activityId, pageable).map(PostResponseDto::from));
        }
        if (memberId != null) {
            return PageResponse.of(postRepository.findByMember_Id(memberId, pageable).map(PostResponseDto::from));
        }

        // 기본
        return PageResponse.of(postRepository.findAll(pageable).map(PostResponseDto::from));

    }

    /**
     * 게시글 단건 조회
     * @param id
     * @return
     */
    @Override
    public PostResponseDto get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다. id = " + id));
        return PostResponseDto.from(post);
    }

    /**
     * 게시글 생성
     * @param dto
     * @return
     */
    @Override
    public PostResponseDto create(PostCreateDto dto) {
        // 현재 로그인 사용자 가져오기
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        long rating = 0L;
        Activity activity = null;

        if (dto.postCategory() == PostCategory.REVIEW) {
            // 리뷰면 activityId와 rating 필수
            if (dto.activityId() == null) {
                throw new CustomException(ErrorCode.BAD_REQUEST, "리뷰 게시글은 activityId가 필요합니다.");
            }
            if (dto.rating() == null) {
                throw new CustomException(ErrorCode.BAD_REQUEST, "리뷰 게시글은 rating이 필요합니다.");
            }

            // 활동 존재 검증
            activity = activityRepository.findById(dto.activityId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "활동을 찾을 수 없습니다."));

            rating = dto.rating().longValue();
        } else {
            // 리뷰가 아니면 activity 연동 없음(= null)
            activity = null;
            rating = 0L; // NOT NULL 컬럼이면 0으로 저장
        }

        Post post = Post.create(
                Member.builder().id(memberId).build(),
                activity, // null 또는 실제 존재하는 Activity
                dto.postCategory(),
                0L,
                rating,
                dto.title(),
                dto.content()
        );
        return PostResponseDto.from(postRepository.save(post));
    }

    @Override
    public PageResponse<PostResponseDto> listMyLiked(Pageable pageable) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        return PageResponse.of(
                postRepository.findLikedByMember(memberId, pageable)
                        .map(PostResponseDto::from)
        );

        }

    @Override
    public PageResponse<PostResponseDto> listMyPosts(Pageable pageable, String category) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        if (category != null && !category.isBlank()) {
            PostCategory cat = parseCategoryOrThrow(category);
            return PageResponse.of(
                    postRepository.findByMember_IdAndCategory(memberId, cat, pageable)
                            .map(PostResponseDto::from)
            );
        }

        return PageResponse.of(
                postRepository.findByMember_Id(memberId, pageable)
                        .map(PostResponseDto::from)
        );

    }

    private PostCategory parseCategoryOrThrow(String category) {
        try {
            return PostCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "유효하지 않은 카테고리: " + category);
        }

    }

    @Override
    public PageResponse<PostResponseDto> listMyReviews(Pageable pageable) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        return PageResponse.of(
                postRepository.findByMember_IdAndCategory(memberId, PostCategory.REVIEW, pageable)
                        .map(PostResponseDto::from)
        );

    }

    @Override
    public PostResponseDto update(Long postId, PostUpdateDto dto) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다. id = " + postId));

        if (!post.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN, "자신의 게시글만 수정할 수 있습니다.");
        }

        post.update(dto.title(), dto.content());

        return PostResponseDto.from(post);
    }


    /**
     * 게시글 삭제
     * @param id
     */
    @Override
    public void delete(Long id) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다. id = " + id));

        if (!post.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN, "자신의 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    /**
     * 게시글 좋아요
     * @param id
     */
    @Override
    public void like(Long id) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다. id = " + id));

        // 이미 좋아요 눌렀는지 확인
        if (postLikeRepository.existsByMember_IdAndPost_Id(memberId, post.getId())) {
            return; // 이미 눌렀으면 무시
        }

        postLikeRepository.save(PostLike.builder()
                .member(Member.builder().id(memberId).build())
                .post(post)
                .build());

        post.increaseLikes();
    }

    /**
     * 게시글 좋아요 취소
     * @param id
     */
    @Override
    public void unlike(Long id) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다. id = " + id));

        // 좋아요 기록 없으면 무시
        if(!postLikeRepository.existsByMember_IdAndPost_Id(memberId, post.getId())) {
            return;
        }

        postLikeRepository.deleteByMember_IdAndPost_Id(memberId, id);

        post.decreaseLikes();
    }
}
