package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetKeywordRecomRes {
    @ApiModelProperty(value = "숙소 검색어 추천")
    private List<String> accomadationRecoms;
    @ApiModelProperty(value = "태그 검색어 추천")
    private List<String> tagRecoms;


}
