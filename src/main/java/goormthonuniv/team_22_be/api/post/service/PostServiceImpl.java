package goormthonuniv.team_22_be.api.post.service;

import goormthonuniv.team_22_be.api.post.dto.PostCreateDto;
import goormthonuniv.team_22_be.api.post.dto.PostResponseDto;
import goormthonuniv.team_22_be.api.post.dto.PostUpdateDto;
import goormthonuniv.team_22_be.api.post.repository.PostLikeRepository;
import goormthonuniv.team_22_be.api.post.repository.PostRepository;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;


    @Override
    public PageResponse<PostResponseDto> list(Pageable pageable, String category, Long activityId, Long memberId) {
        return null;
    }

    @Override
    public PostResponseDto get(Long id) {
        return null;
    }

    @Override
    public PostResponseDto create(PostCreateDto dto) {
        return null;
    }

    @Override
    public PostResponseDto update(Long id, PostUpdateDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void like(Long id) {

    }

    @Override
    public void unlike(Long id) {

    }
}
