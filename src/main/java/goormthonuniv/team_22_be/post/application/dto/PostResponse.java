package goormthonuniv.team_22_be.post.application.dto;

import goormthonuniv.team_22_be.post.domain.model.Post;
import goormthonuniv.team_22_be.post.domain.model.PostCategory;

public record PostResponse(
        Long id,
        Long memberId,
        Long activityId,
        PostCategory category,
        Long likes,
        Integer rating,
        String title,
        String content
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getMember().getId(),
                post.getActivity() != null ? post.getActivity().getId() : null,
                post.getCategory(),
                post.getLikes(),
                post.getRating() != null ? post.getRating().intValue() : null,
                post.getTitle(),
                post.getContent()
        );
    }
}
