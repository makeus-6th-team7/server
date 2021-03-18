package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostFeedRes {
    public PostFeedRes() {
        super();
    }
    @ApiModelProperty(value = "생성한 게시물 id(식별자)")
    private int feedId;
}
