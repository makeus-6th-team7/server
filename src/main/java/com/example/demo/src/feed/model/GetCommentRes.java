package com.example.demo.src.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentRes {
    private int commentId;
    private int userIdx;
    private String userId;
    private String profileImgUrl;
    private String content;
    private String createdAt;

    private int likeNum = 0;
    private boolean isLiked = false;

    public GetCommentRes(int commentId, int userIdx, String userId, String profileImgUrl, String content, String createdAt) {
        this.commentId = commentId;
        this.userIdx = userIdx;
        this.userId = userId;
        this.profileImgUrl = profileImgUrl;
        this.content = content;
        this.createdAt = createdAt;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}

