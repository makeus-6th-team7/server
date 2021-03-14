package com.example.demo.src.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetFeedFromDao {
    private int userIdx;
    private String userId;
    private String profileImgUrl;
    private int temperature;
    private int retouchedDegree;
    private String review;
    private String title;
    private Double longitude;
    private Double latitude;
    private int price;
    private String period;
    private int likeNum;
    private boolean checkLike;
    private String createdAt;
    private int viewNum;
    private int savedNum;
    private int commentNum;
    private boolean checkReport;
    private int feedImgNum;

    private List<String> pros = null;
    private List<String> cons = null;
    private List<String> feedImgUrls = null;
    private List<String> tags = null;

    public GetFeedFromDao(int userIdx, String userId, String profileImgUrl, int temperature, int retouchedDegree, String review, String title, Double longitude, Double latitude, int price, String period, int likeNum, boolean checkLike, String createdAt, int viewNum, int savedNum, int commentNum, boolean checkReport, int feedImgNum) {
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
        this.checkLike = checkLike;
        this.createdAt = createdAt;
        this.viewNum = viewNum;
        this.savedNum = savedNum;
        this.commentNum = commentNum;
        this.checkReport = checkReport;
        this.feedImgNum = feedImgNum;
    }


}
