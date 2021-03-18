package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentRes {
    public PostCommentRes() {
        super();
    }

    @ApiModelProperty(value = "입력한 댓글 id (식별자)")
    private int commentId;
}
