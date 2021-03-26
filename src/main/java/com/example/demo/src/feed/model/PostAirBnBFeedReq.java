package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=false)
public class PostAirBnBFeedReq extends PostFeedReq{
    @NotBlank(message = "airBnbLink 값을 입력해 주세요")
    @ApiModelProperty(value = "에어비앤비 링크 ")
    private String airBnBLink;
    @ApiModelProperty(value = "숙소 위치 부가설명")
    private String additionalLocation;
    //추후 변경
    @NotBlank(message = "address 값을 입력해 주세요")
    @ApiModelProperty(value = "숙소 위치 -동까지",example="제주도 제주시 도두동")
    private String address;
}
