package goormthonuniv.team_22_be.post.application.dto;

import goormthonuniv.team_22_be.post.domain.model.Post;
import goormthonuniv.team_22_be.post.domain.model.PostCategory;

public record PostResponseDto(
        Long id, Long memberId, Long activityId,
        PostCategory category, Long likes, Integer rating,
        String title, String content
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
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
