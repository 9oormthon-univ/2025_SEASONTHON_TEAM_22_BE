package goormthonuniv.team_22_be.post.domain.repository;

import goormthonuniv.team_22_be.post.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> findLikedByMember(Long memberId, Pageable pageable);
}
