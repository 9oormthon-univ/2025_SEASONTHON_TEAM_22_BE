package goormthonuniv.team_22_be.api.post.repository;

import goormthonuniv.team_22_be.api.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMember_IdAndPost_Id(Long member_Id, Long post_Id);
    void deleteByMember_IdAndPost_Id(Long member_Id, Long post_Id);
}
