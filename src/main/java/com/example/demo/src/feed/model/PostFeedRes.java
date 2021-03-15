package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;

public class PostFeedRes {
    public PostFeedRes() {
        super();
    }
    @ApiModelProperty(value = "생성한 게시물 id(식별자)")
    private int feedId;
}
