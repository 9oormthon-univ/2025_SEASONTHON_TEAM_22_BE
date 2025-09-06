package goormthonuniv.team_22_be.post.domain.repository;

import goormthonuniv.team_22_be.post.domain.model.Post;
import goormthonuniv.team_22_be.post.domain.model.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Page<Post> findByCategory(PostCategory category, Pageable pageable);

    Page<Post> findByActivity_Id(Long activityId, Pageable pageable);

    Page<Post> findByMemberId(Long memberId, Pageable pageable);

    Page<Post> findByMemberIdAndCategory(Long memberId, PostCategory category, Pageable pageable);
}
