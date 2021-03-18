package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.utils.customBeanValidation.DateCheck;

@Data
//생성자 어떡할지 생각
//TO-DO:
// 사용 기기 추가
public class PostFeedReq {
    @NotBlank(message = "title 값을 입력해 주세요")
    @ApiModelProperty(value = "숙소 이름(지도API에서 받은 정확한 상호명)", example="롯데 호텔 월드")
    private String title;
    @ApiModelProperty(value = "에어비앤비 일 경우: true / 아닐 경우: false(디폴트 값)", example = "true")
    private boolean checkAirBnB = false;
    //validation 필요
    @ApiModelProperty(value = "에어비앤비 링크 (에어비앤비일 경우 필수 입력)")
    private String airBnBLink;
    @NotEmpty(message = "feedImgUrl 값을 입력해 주세요")
    @ApiModelProperty(value = "숙소 사진 URL")
    private List<String> feedImgUrls;
    @NotBlank
    @DateCheck(message = "startPeriod에 올바른 date 형식을 입력해 주세요")
    @ApiModelProperty(value = "숙박 기간-시작날짜", example="2021-01-20")
    private String startPeriod;
    @NotBlank
    @DateCheck(message = "endPeriod에 올바른 date 형식을 입력해 주세요")
    @ApiModelProperty(value = "숙박 기간-끝날짜", example="2021-01-22")
    private String endPeriod;
    @Min(value = 1, message = "price에 올바른 값을 입력해주세요 (0초과)")
    @ApiModelProperty(value = "가격 (1박 기준) - 0은 입력 안된 걸로 처리됨", example = "200000")
    private int price;
    //validation 필요
    @ApiModelProperty(value = "숙소 위치 좌표 - 경도 (에어비앤비 아닌 경우에만 입력)",example = "127.0980274")
    private Double longitude;
    //validation 필요
    @ApiModelProperty(value = "숙소 위치 좌표 - 위도 (에어비앤비 아닌 경우에만 입력)",example = "37.5112348")
    private Double latitude;
    //추후 변경
    @ApiModelProperty(value = "숙소 위치 -동까지(에어비앤비일 경우 필수 입력)",example="제주도 제주시 도두동")
    private String address;
    @NotBlank(message = "review 값을 입력해 주세요")
    @ApiModelProperty(value = "후기")
    private String review;
    @ApiModelProperty(value = "장점 리스트",example = "[ \"깨끗함\", \"룸서비스\", \"부대시설\" ]")
    private List<String> pros = new ArrayList<String>();
    @ApiModelProperty(value = "단점 리스트", example = "[ \"가성비\"]")
    private List<String> cons = new ArrayList<String>();
    @ApiModelProperty(value = "태그 리스트",example = "[ \"잠실호텔\", \"럭셔리\" ]")
    private List<String> tags = new ArrayList<String>();
    @ApiModelProperty(value = "보정정도",example = "5")
    @Min(value = 0, message = "retouchedDegree에 0과 5사이 값을 입력해 주세요")
    @Max(value = 5, message = "retouchedDegree에 0과 5사이 값을 입력해 주세요")
    private int retouchedDegree ;

    public boolean isAirBnB() {
        return this.checkAirBnB;
    }
}
