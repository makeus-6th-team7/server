package com.example.demo.src.feed.model;

        import io.swagger.annotations.ApiModelProperty;
        import lombok.Data;

@Data
public class GetHomeTabFeedReq {
    public GetHomeTabFeedReq(){
        super();
    }
    @ApiModelProperty(value="pop:인기, new:최신",example = "pop")
    private String type;
}
