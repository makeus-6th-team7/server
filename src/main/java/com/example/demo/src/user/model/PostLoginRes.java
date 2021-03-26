package com.example.demo.src.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginRes {

    @ApiModelProperty(value = "로그인한 유저 식별자")
    private int userIdx;
    @ApiModelProperty(value = "로그인한 유저의 JWT")
    private String jwt;
    @ApiModelProperty(value = "새로 회원가입한 유저일 경우 : true / 기존 회원일 경우 : false (새로운 회원일 경우 id 설정 필요)")
    private boolean newUser;
}
