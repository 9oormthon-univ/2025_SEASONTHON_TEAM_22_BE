package goormthonuniv.team_22_be.post.domain.repository;

import goormthonuniv.team_22_be.post.domain.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMember_IdAndPost_Id(Long member_Id, Long post_Id);
    void deleteByMember_IdAndPost_Id(Long member_Id, Long post_Id);
}
