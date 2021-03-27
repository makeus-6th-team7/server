package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetKeywordRecomReq {
    public GetKeywordRecomReq(){
        super();
    }
    @NotBlank(message = "검색어를 입력해 주세요")
    @ApiModelProperty(value = "검색 키워드",example = "럭")
    public String keyword;
}
