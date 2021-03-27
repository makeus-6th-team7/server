package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetHomeFeedRes {
    private List<Feed> feeds;
    @Data
    @AllArgsConstructor
    public class Feed{
        @ApiModelProperty(value="피드 식별자",example = "1")
        private int feedId;
        @ApiModelProperty(value="피드 제목(상호명)",example = "롯데 호텔 월드")
        private String title;
        @ApiModelProperty(value="피드 대표 사진 url")
        private String feedImgUrl;
        @ApiModelProperty(value="보정정도",example = "5")
        private int retouchedDegree;
        @ApiModelProperty(value="신뢰도",example = "50")
        private int temperature;
    }

    public GetHomeFeedRes() {
    }
}
