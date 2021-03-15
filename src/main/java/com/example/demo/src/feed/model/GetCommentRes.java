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
        @ApiModelProperty(value = "댓글 식별자")
        private int commentId;
        @ApiModelProperty(value = "댓글을 작성한 유저 식별자")
        private int userIdx;
        @ApiModelProperty(value = "댓글을 작성한 유저 ID")
        private String userId;
        @ApiModelProperty(value = "댓글을 작성한 유저 프로필 사진 URL")
        private String profileImgUrl;
        @ApiModelProperty(value = "댓글 내용")
        private String content;
        @ApiModelProperty(value = "댓글 생성 시간", example="2일전")
        private String createdAt;
        @ApiModelProperty(value = "좋아요 개수")
        private int likeNum = 0;
        @ApiModelProperty(value = "접속한 사용자가 댓글 좋아요했는지 표시(좋아요 했을 경우: true, 안했을 경우: false)")
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
    @ApiModelProperty(value = "접속한 유저의 프로필 사진 URL")
    private String profileImgUrl;

    public GetCommentRes() {
    }
    public GetCommentRes(List<Comment> comments) {
        this.comments = comments;
    }


}

