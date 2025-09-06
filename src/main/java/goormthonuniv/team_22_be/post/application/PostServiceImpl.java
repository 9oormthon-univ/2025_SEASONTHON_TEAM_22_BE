package goormthonuniv.team_22_be.post.application;

import goormthonuniv.team_22_be.activity.domain.model.Activity;
import goormthonuniv.team_22_be.activity.domain.repository.ActivityRepository;
import goormthonuniv.team_22_be.member.domain.repository.MemberRepository;
import goormthonuniv.team_22_be.post.application.dto.CreatePostRequest;
import goormthonuniv.team_22_be.post.application.dto.PostResponse;
import goormthonuniv.team_22_be.post.application.dto.UpdatePostRequest;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ActivityRepository activityRepository;
    private final MemberRepository memberRepository;

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
    public PageResponse<PostResponse> getPostPage(Pageable pageable, String category, Long activityId, Long memberId) {

        if(category != null && !category.isBlank()) {
            PostCategory cat;
            try {
                cat = PostCategory.valueOf(category.toUpperCase());
            }
            catch(IllegalArgumentException e) {
                throw new CustomException(ErrorCode.BAD_REQUEST, "유효하지 않은 카테고리 : " + category);
            }
            return PageResponse.of(postRepository.findByCategory(cat, pageable).map(PostResponse::from));
        }
        if (activityId != null) {
            return PageResponse.of(postRepository.findByActivity_Id(activityId, pageable).map(PostResponse::from));
        }
        if (memberId != null) {
            return PageResponse.of(postRepository.findByMemberId(memberId, pageable).map(PostResponse::from));
        }

        // 기본
        return PageResponse.of(postRepository.findAll(pageable).map(PostResponse::from));

    }

    /**
     * 게시글 단건 조회
     * @param postId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다. id = " + postId));
        return PostResponse.from(post);
    }

    /**
     * 게시글 생성
     * @param request
     * @return
     */
    @Transactional
    @Override
    public Long createPost(Long memberId, CreatePostRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        long rating = 0L;
        Activity activity = null;

        if (request.postCategory() == PostCategory.REVIEW) {
            if (request.activityId() == null) {
                throw new CustomException(ErrorCode.BAD_REQUEST, "리뷰 게시글은 activityId가 필요합니다.");
            }
            if (request.rating() == null) {
                throw new CustomException(ErrorCode.BAD_REQUEST, "리뷰 게시글은 rating이 필요합니다.");
            }

            activity = activityRepository.findById(request.activityId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "활동을 찾을 수 없습니다."));

            rating = request.rating().longValue();
        } else {
            activity = null;
            rating = 0L;
        }

        Post post = Post.create(
                member,
                activity,
                request.postCategory(),
                0L,
                rating,
                request.title(),
                request.content()
        );

        return postRepository.save(post).getId();
    }

    @Transactional
    @Override
    public PostResponse updatePost(Long memberId, Long postId, UpdatePostRequest dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (!post.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        post.update(dto.title(), dto.content());

        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<PostResponse> listMyLiked(Long memberId, Pageable pageable) {
        return PageResponse.of(
                postRepository.findLikedByMember(memberId, pageable)
                        .map(PostResponse::from)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<PostResponse> listMyPosts(Long memberId, Pageable pageable, String category) {
        if (category != null && !category.isBlank()) {
            PostCategory cat = parseCategoryOrThrow(category);
            return PageResponse.of(
                    postRepository.findByMemberIdAndCategory(memberId, cat, pageable)
                            .map(PostResponse::from)
            );
        }

        return PageResponse.of(
                postRepository.findByMemberId(memberId, pageable)
                        .map(PostResponse::from)
        );

    }

    private PostCategory parseCategoryOrThrow(String category) {
        try {
            return PostCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "유효하지 않은 카테고리: " + category);
        }

    }

    /**
     * 게시글 삭제
     * @param memberId
     * @param postId
     */
    @Override
    public void deletePost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (!post.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN, "자신의 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    /**
     * 게시글 좋아요
     * @param memberId
     * @param postId
     */
    @Transactional
    @Override
    public void like(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        // 이미 좋아요 눌렀는지 확인
        if (postLikeRepository.existsByMember_IdAndPost_Id(memberId, post.getId())) {
            return;
        }

        postLikeRepository.save(PostLike.builder()
                .member(Member.builder().id(memberId).build())
                .post(post)
                .build());

        post.increaseLikes();
    }

    /**
     * 게시글 좋아요 취소
     * @param memberId
     * @param postId
     */
    @Override
    public void unlike(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        // 좋아요 기록 없으면 무시
        if(!postLikeRepository.existsByMember_IdAndPost_Id(memberId, post.getId())) {
            return;
        }

        postLikeRepository.deleteByMember_IdAndPost_Id(memberId, postId);

        post.decreaseLikes();
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<PostResponse> listMyReviews(Long memberId, Pageable pageable) {
        return PageResponse.of(
                postRepository.findByMemberIdAndCategory(memberId, PostCategory.REVIEW, pageable)
                        .map(PostResponse::from)
        );
    }
}
