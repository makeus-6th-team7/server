package com.example.demo.src.feed.model;

import lombok.Data;

import java.util.List;

@Data
public class GetFeedRes {
    private int userIdx;
    private String userId;
    private String profileImgUrl;
    private int temperature;
    private int retouchedDegree;
    private String review;
    private String title;
    private String address;
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

    public GetFeedRes(int userIdx, String userId, String profileImgUrl, int temperature, int retouchedDegree, String review, String title, String address, int price, String period, int likeNum, boolean checkLike, String createdAt, int viewNum, int savedNum, int commentNum, boolean checkReport, int feedImgNum) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.profileImgUrl = profileImgUrl;
        this.temperature = temperature;
        this.retouchedDegree = retouchedDegree;
        this.review = review;
        this.title = title;
        this.address = address;
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

    public GetFeedRes(int userIdx, String userId, String profileImgUrl, int temperature, int retouchedDegree, String review, String title, String address, int price, String period, int likeNum, boolean checkLike, String createdAt, int viewNum, int savedNum, int commentNum, boolean checkReport, int feedImgNum, List<String> pros, List<String> cons, List<String> feedImgUrls) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.profileImgUrl = profileImgUrl;
        this.temperature = temperature;
        this.retouchedDegree = retouchedDegree;
        this.review = review;
        this.title = title;
        this.address = address;
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
        this.pros = pros;
        this.cons = cons;
        this.feedImgUrls = feedImgUrls;
    }
}
