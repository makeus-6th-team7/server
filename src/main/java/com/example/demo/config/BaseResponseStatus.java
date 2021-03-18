package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    //추후 적용?
    LOG_IN_SUCCESS(true, 1001, "로그인에 성공하였습니다."),
    SIGN_IN_SUCCESS(true, 1002, "회원가입에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_ACCESS_TOKEN(false,2004,"유효하지 않은 accessToken 입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    // [GET] /feeds
    INVALID_FEED_ID(false, 2020, "존재하지 않는 feedId입니다."),
    INVALID_COMMENT_ID(false, 2021, "존재하지 않는 commentId입니다."),
    INVALID_USER_REQ(false, 2022, "권한이 없는 유저의 요청입니다."),

    // [POST] /feeds
    EMPTY_TITLE(false, 2030, "title 값을 입력해 주세요."),
    EMPTY_AIRBNB_LINK(false, 2032, "airBnbLink 값을 입력해 주세요."),
    EMPTY_FEEDIMG_URLS(false, 2033, "feedImgUrls 값을 입력해 주세요."),
    EMPTY_START_PERIOD(false, 2034, "startPeriod 값을 입력해 주세요."),
    EMPTY_END_PERIOD(false, 2035, "endPeriod 값을 입력해 주세요."),
    EMPTY_PRICE(false, 2036, "price 값을 입력해 주세요."),
    EMPTY_LONGITUTDE(false, 2037, "longitude 값을 입력해 주세요."),
    EMPTY_LATITUDE(false, 2038, "latitude 값을 입력해 주세요."),
    EMPTY_ADDRESS(false, 2039, "address 값을 입력해 주세요."),
    EMPTY_REVIEW(false, 2040, "review 값을 입력해 주세요."),
    EMPTY_RETOUCHED_DEGREE(false, 2041, "retouchedDegree 값을 입력해 주세요"),

    INVALID_TYPE_TITLE(false, 2050, "title에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_CHECK_AIRBNB(false, 2051, "isAirBnB에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_AIRRBNB_LINK(false, 2052, "airBnbLink에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_FEEDIMG_URLS(false, 2053, "feedImgUrls에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_START_PERIOD(false, 2054, "startPeriod에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_ENDP_ERIOD(false, 2055, "endPeriod에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_PRICE(false, 2056, "price에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_LONGITUTDE(false, 2057, "longitude에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_LATITUDE(false, 2058, "latitude에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_ADDRESS(false, 2059, "address에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_REVIEW(false, 2060, "review에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_PROS(false, 2061, "pros에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_CONS(false, 2062, "cons에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_TAGS(false, 2063, "tags에 잘못된 타입이 입력되었습니다."),
    INVALID_TYPE_RETOUCHED_DEGREE(false, 2064, "retouchedDegree에 잘못된 타입이 입력되었습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),




    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    //SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다.")

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    KAKAO_LOCATION_SERVER_ERROR(false, 4020, "카카오 서버에서 주소정보 요청에 실패했습니다."),
    NAVER_SHOPPING_SERVER_ERROR(false, 4021, "네이버 서버에서 쇼핑정보 요청에 실패했습니다.");

    // 5000 : 필요시 만들어서 쓰기
    // 6000 : 필요시 만들어서 쓰기


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
