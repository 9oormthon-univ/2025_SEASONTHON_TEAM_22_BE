package goormthonuniv.team_22_be.post.domain.service;

import goormthonuniv.team_22_be.post.application.dto.PostCreateDto;
import goormthonuniv.team_22_be.post.application.dto.PostResponseDto;
import goormthonuniv.team_22_be.post.application.dto.PostUpdateDto;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PageResponse<PostResponseDto> list(Pageable pageable, String category, Long activityId, Long memberId);
    PostResponseDto get(Long id);
    PostResponseDto update (Long postId, PostUpdateDto dto);
    PostResponseDto create(PostCreateDto dto);
    // 내가 찜 한 게시글 목록
    PageResponse<PostResponseDto> listMyLiked(Pageable pageable);
    // 내가 신청한 게시글 목록

    // 내가 쓴 글


    void delete(Long id);
    void like(Long id);
    void unlike(Long id);
}