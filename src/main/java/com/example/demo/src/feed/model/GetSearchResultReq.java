package com.example.demo.src.feed.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class GetSearchResultReq {
    public GetSearchResultReq(){
        super();
    }
    @NotBlank(message = "검색어를 입력해 주세요")
    @ApiModelProperty(value = "검색 키워드")
    public String keyword;
//    @ApiModelProperty(value = "최소 가격(필터)")
//    public int minPrice = 0;
//    @ApiModelProperty(value = "최대 가격(필터)")
//    public int maxPrice = Integer.MAX_VALUE;
    // To-do: 일단은 default로 일 년 이내로 해놓음->추후 변경필요
    // 이중에 없으면 예외처리 추가
//    @ApiModelProperty(value = "기간 (필터) ex:모든 날짜/지난 1시간/지난 1일/지난 1주/지난 1개월/지난 1년 중 하나",example = "지난 1년")
//    public String period = "모든 날짜";
//    @ApiModelProperty(value = "장점 리스트(필터)")
//    public List<String> pros = null;
//    @ApiModelProperty(value = "단점 리스트(필터)")
//    public List<String> cons = null;

    //To-do: 지역추가
//    @ApiModelProperty(value = "지역(필터)")
//    public String address = null;

}
