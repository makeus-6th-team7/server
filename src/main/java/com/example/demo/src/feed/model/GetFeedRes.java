package com.example.demo.src.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetFeedRes {
    private int userIdx;
    private String userId;
    private String profileImgUrl;
    private int temperature;
    private int retouchedDegree;
    private String review;
    private String title;
    private String longitude;
    private String latitude;
    private int price;
    private String period;
    private int likeNum;
    private boolean isLIked;
    private String createdAt;
    private int viewNum;
    private int savedNum;
    private int commentNum;
    private boolean isReported;
    private int feedImgNum;

    private List<String> pros = null;
    private List<String> cons = null;
    private List<String> feedImgUrls = null;

    public GetFeedRes(int userIdx, String userId, String profileImgUrl, int temperature, int retouchedDegree, String review, String title, String longitude, String latitude, int price, String period, int likeNum, boolean isLIked, String createdAt, int viewNum, int savedNum, int commentNum, boolean isReported, int feedImgNum) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.profileImgUrl = profileImgUrl;
        this.temperature = temperature;
        this.retouchedDegree = retouchedDegree;
        this.review = review;
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
        this.price = price;
        this.period = period;
        this.likeNum = likeNum;
        this.isLIked = isLIked;
        this.createdAt = createdAt;
        this.viewNum = viewNum;
        this.savedNum = savedNum;
        this.commentNum = commentNum;
        this.isReported = isReported;
        this.feedImgNum = feedImgNum;
    }


}