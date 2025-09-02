package goormthonuniv.team_22_be.api.post.service;

import goormthonuniv.team_22_be.api.post.dto.PostCreateDto;
import goormthonuniv.team_22_be.api.post.dto.PostResponseDto;
import goormthonuniv.team_22_be.api.post.dto.PostUpdateDto;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PageResponse<PostResponseDto> list(Pageable pageable, String category, Long activityId, Long memberId);
    PostResponseDto get(Long id);
    PostResponseDto create(PostCreateDto dto);
    PostResponseDto update(Long id, PostUpdateDto dto);
    void delete(Long id);
    void like(Long id);
    void unlike(Long id);
}