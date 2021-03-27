package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class GetSearchResultRes {
    @ApiModelProperty(value = "검색 결과 리스트")
    private List<SearchResult> searchResults;

    public GetSearchResultRes(){}

    @Data
    @AllArgsConstructor
    public class SearchResult{
        private int feedId;
        private String feedImgUrl;
    }
}

