package goormthonuniv.team_22_be.post.domain.repository;

import goormthonuniv.team_22_be.post.domain.model.Post;
import goormthonuniv.team_22_be.post.domain.model.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByCategory(PostCategory category, Pageable pageable);
    Page<Post> findByActivity_Id(Long activityId, Pageable pageable);
    Page<Post> findByMember_Id(Long memberId, Pageable pageable);
    boolean existsByIdAndMember_Id(Long postId, Long memberId);
}
