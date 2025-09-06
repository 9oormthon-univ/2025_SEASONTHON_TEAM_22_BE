package goormthonuniv.team_22_be.post.infrastructe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import goormthonuniv.team_22_be.post.domain.model.Post;
import goormthonuniv.team_22_be.post.domain.repository.PostRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static goormthonuniv.team_22_be.post.domain.model.QPost.post;
import static goormthonuniv.team_22_be.post.domain.model.QPostLike.postLike;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Post> findLikedByMember(Long memberId, Pageable pageable) {
        List<Post> posts = jpaQueryFactory
                .select(post)
                .from(postLike)
                .join(postLike.post, post)
                .where(postLike.member.id.eq(memberId))
                .orderBy(postLike.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(postLike.count())
                .from(postLike)
                .where(postLike.member.id.eq(memberId))
                .fetchOne();

        return PageableExecutionUtils.getPage(posts, pageable, () -> total != null ? total : 0);
    }
}
