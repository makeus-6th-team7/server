package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.utils.customBeanValidation.DateCheck;


//생성자 어떡할지 생각
//TO-DO:
// 사용 기기 추가
@Data
public class PostFeedReq {
    @NotEmpty(message = "feedImgUrl 값을 입력해 주세요")
    @ApiModelProperty(value = "숙소 사진 URL")
    private List<String> feedImgUrls;
    @NotBlank(message = "startPeriod에 값을 입력해 주세요")
    @DateCheck(message = "startPeriod에 올바른 date 형식을 입력해 주세요")
    @ApiModelProperty(value = "숙박 기간-시작날짜", example="2021-01-20")
    private String startPeriod;
    @NotBlank(message = "endPeriod에 값을 입력해 주세요")
    @DateCheck(message = "endPeriod에 올바른 date 형식을 입력해 주세요")
    @ApiModelProperty(value = "숙박 기간-끝날짜", example="2021-01-22")
    private String endPeriod;
    @Min(value = 1, message = "price에 올바른 값을 입력해주세요 (0초과)")
    @ApiModelProperty(value = "가격 (1박 기준) - 0은 입력 안된 걸로 처리됨", example = "200000")
    private int price;
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
    @ApiModelProperty(value = "사용 도구",example = "기본카메라")
    private String photoTool;
}
