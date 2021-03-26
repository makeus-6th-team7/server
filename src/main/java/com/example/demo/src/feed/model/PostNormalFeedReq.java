package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=false)
public class PostNormalFeedReq extends PostFeedReq{
    @NotBlank(message = "title 값을 입력해 주세요")
    @ApiModelProperty(value = "숙소 이름(지도API에서 받은 정확한 상호명)", example="롯데 호텔 월드")
    private String title;
    @ApiModelProperty(value = "숙소 위치 좌표 - 경도",example = "127.0980274")
    private Double longitude;
    @ApiModelProperty(value = "숙소 위치 좌표 - 위도",example = "37.5112348")
    private Double latitude;
}
