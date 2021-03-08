package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentRes {

    @Getter
    @Setter
    @AllArgsConstructor
    public class Comment{
        private int commentId;
        private int userIdx;
        private String userId;
        private String profileImgUrl;
        private String content;
        private String createdAt;

        private int likeNum = 0;
        private boolean checkLike = false;

        public Comment(int commentId, int userIdx, String userId, String profileImgUrl, String content, String createdAt) {
            this.commentId = commentId;
            this.userIdx = userIdx;
            this.userId = userId;
            this.profileImgUrl = profileImgUrl;
            this.content = content;
            this.createdAt = createdAt;
        }
    }

    private List<Comment> comments;
    private String profileImgUrl;

    public GetCommentRes() {
    }
    public GetCommentRes(List<Comment> comments) {
        this.comments = comments;
    }


}

