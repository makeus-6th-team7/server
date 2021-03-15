package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentReq {
    public PostCommentReq() {
        super();
    }

    @ApiModelProperty(value = "댓글 내용", example = "너무 좋아요!")
    private String content;
}
