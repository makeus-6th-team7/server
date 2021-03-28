package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetFeedRes {
    @ApiModelProperty(value = "피드를 게시한 유저의 식별자")
    private int userIdx;
    @ApiModelProperty(value = "피드를 게시한 유저의 ID")
    private String userId;
    @ApiModelProperty(value = "피드를 게시한 유저의 프로필 사진 URL")
    private String profileImgUrl;
    @ApiModelProperty(value = "피드를 게시한 유저의 온도")
    private int temperature;
    @ApiModelProperty(value = "에어비앤비인지 표시(에어비앤비인 경우: true, 아닌 경우: false)")
    private boolean checkAirBnB;
    @ApiModelProperty(value = "사진 보정 정도")
    private int retouchedDegree;
    @ApiModelProperty(value = "후기")
    private String review;
    @ApiModelProperty(value = "사용 도구", example = "카메라어플 사용. 보정필터 사용. ")
    private String photoTool;
    @ApiModelProperty(value = "숙소 이름")
    private String title;
    @ApiModelProperty(value = "숙소 위치", example = "서울특별시 송파구 올림픽로 240 롯데월드")
    private String address;
    @ApiModelProperty(value = "근처", example = "홍대입구역")
    private String additionalLocation;
    @ApiModelProperty(value = "숙소 가격")
    private int price;
    @ApiModelProperty(value = "기간", example = "2021.01.18 - 2021.01.20")
    private String period;
    @ApiModelProperty(value = "좋아요 개수")
    private int likeNum;
    @ApiModelProperty(value = "접속중인 사용자가 좋아요 했는지 표시 (좋아요 했을 경우: true, 안했을 경우: false)")
    private boolean checkLike;
    @ApiModelProperty(value = "피드 생성 시기", example="10분전")
    private String createdAt;
    @ApiModelProperty(value = "조회수")
    private int viewNum;
    @ApiModelProperty(value = "쨈 개수")
    private int savedNum;
    @ApiModelProperty(value = "댓글 개수")
    private int commentNum;
    @ApiModelProperty(value = "접속중인 사용자가 신고한 게시물인지 표시(신고 했을 경우: true, 안했을 경우: false)")
    private boolean checkReport;
    @ApiModelProperty(value = "사진 개수")
    private int feedImgNum;
    
    @ApiModelProperty(value = "장점 리스트", example = "[\"깨끗함\", \"룸서비스\", \"부대시설\"]")
    private List<String> pros = null;
    @ApiModelProperty(value = "단점 리스트", example = "[\"가성비\"]")
    private List<String> cons = null;
    @ApiModelProperty(value = "피드 이미지 url 리스트", example = "[\"https://media-cdn.tripadvisor.com/media/photo-m/1280/16/63/d5/dc/caption.jpg\", " +
                                                                "\"https://media-cdn.tripadvisor.com/media/photo-m/1280/16/63/d5/c4/caption.jpg\"]")
    private List<String> feedImgUrls = null;
    @ApiModelProperty(value = "태그 리스트", example = "[\"잠실호텔\", \"럭셔리\"]")
    private List<String> tags = null;

    public GetFeedRes(int userIdx, String userId, String profileImgUrl, int temperature, boolean checkAirBnB,
                      int retouchedDegree, String review, String photoTool, String title, String address, String additionalLocation,
                      int price, String period, int likeNum, boolean checkLike, String createdAt,
                      int viewNum, int savedNum, int commentNum, boolean checkReport, int feedImgNum,
                      List<String> pros, List<String> cons, List<String> feedImgUrls, List<String> tags) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.profileImgUrl = profileImgUrl;
        this.temperature = temperature;
        this.checkAirBnB = checkAirBnB;
        this.retouchedDegree = retouchedDegree;
        this.review = review;
        this.photoTool = photoTool;
        this.title = title;
        this.address = address;
        this.additionalLocation = additionalLocation;
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
        this.tags = tags;
    }
}
