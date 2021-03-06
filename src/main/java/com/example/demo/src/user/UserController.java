package com.example.demo.src.user;

import com.example.demo.config.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    //카카오 code 받기
    @GetMapping("/auth/kakao/callback") //redirect_uri : users/auth/kakao/callback
    public @ResponseBody
    String kakaoCallback(String code) {
        return "코드는 :" + code;
    }

    /**
     * 카카오 로그인 API
     * [POST] users/log-in/kakao
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/log-in/kakao")
    @ApiOperation(value = "카카오 로그인 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2004, message = "유효하지 않은 accessToken 입니다.",response = BaseResponse.class),
    })
    public BaseResponse<PostLoginRes> kakaoLogin(@RequestBody PostLoginReq postLoginReq) throws BaseException {
        KakaoProfileRes kakaoProfile = null;
        PostLoginRes postLoginRes = null;
        try {
            kakaoProfile = userProvider.getKakaoProfile(postLoginReq);
        } catch (BaseException exception) {
            System.out.println(exception.getMessage());
            return new BaseResponse<>((exception.getStatus()));
        }
        // 이미 존재하는 계정이면 로그인
        int kakaoId = kakaoProfile.getId();
        if (userProvider.checkUserByKakaoId(kakaoId) == 1){
            try {
                postLoginRes  = userProvider.logIn(kakaoId);
                // 아이디가 등록됐는지 체크
                if(userProvider.checkUserIdIsKakaoId(postLoginRes.getUserIdx(),kakaoId)){
                    postLoginRes.setNewUser(true);
                }
                return new BaseResponse<>(postLoginRes);
            } catch (BaseException exception) {
                System.out.println(exception.getMessage());
                return new BaseResponse<>((exception.getStatus()));
            }
        }
        // 존재하지 않는 계정이면 회원가입
        else{
            try {
                postLoginRes  = userService.createUserByKakao(kakaoProfile);
                return new BaseResponse<>(postLoginRes);
            } catch (BaseException exception) {
                System.out.println(exception.getMessage());
                return new BaseResponse<>((exception.getStatus()));
            }
        }
    }
    /**
     * 아이디 설정 API
     * [POST] users/id
     * @return BaseResponse<>
     */
    // Body
    @ResponseBody
    @PostMapping("/id")
    @ApiOperation(value = "아이디 설정 API")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2011, message = "다른 분이 사용하고 있는 닉네임이에요.",response = BaseResponse.class)
    })
    public BaseResponse postUserId(@RequestBody PostUserIdReq postUserIdReq){

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();
            userService.postUserId(userIdx,postUserIdReq.getUserId());
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    public BaseResponse<PostUserRes> createUser(@RequestBody PostLoginReq postLoginReq) {
//        try{
//            KakaoProfile kakaoProfile = UserProvider.getKakaoProfile(postLoginReq);
//            return new BaseResponse<>(postUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


//
//    /**
//     * 회원 조회 API
//     * [GET] /users
//     * 회원 번호 및 이메일 검색 조회 API
//     * [GET] /users? Email=
//     * @return BaseResponse<List<GetUserRes>>
//     */
//    //Query String
//    @ResponseBody
//    @GetMapping("") // (GET) http://52.79.187.77/app/users
//    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
//        try{
//            if(Email == null){
//                List<GetUserRes> getUsersRes = userProvider.getUsers();
//                return new BaseResponse<>(getUsersRes);
//            }
//            // Get Users
//            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
//            return new BaseResponse<>(getUsersRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 회원 1명 조회 API
//     * [GET] /users/:userIdx
//     * @return BaseResponse<GetUserRes>
//     */
//    // Path-variable
//    @ResponseBody
//    @GetMapping("/{userIdx}") // (GET) http://52.79.187.77/app/users/:userIdx
//    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
//        // Get Users
//        try{
//            GetUserRes getUserRes = userProvider.getUser(userIdx);
//            return new BaseResponse<>(getUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//    }
//
//    /**
//     * 회원가입 API
//     * [POST] /users
//     * @return BaseResponse<PostUserRes>
//     */
//    // Body
//    @ResponseBody
//    @PostMapping("")
//    public BaseResponse<PostUserRes> createUser_(@RequestBody PostUserReq postUserReq) {
//        // TODO: email 관련한 짧은 validation 예시. 그 외 더 부가적으로 추가하기
//        if(postUserReq.getEmail() == null){
//            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
//        }
//        postUserReq.getAccessToken();
//        if(postUserReq.getAccessToken() == null){
//            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
//        }
//        //이메일 정규표현
//        if(!isRegexEmail(postUserReq.getEmail())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
//        try{
//            PostUserRes postUserRes = userService.createUser(postUserReq);
//            return new BaseResponse<>(postUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//    /**
//     * 로그인 API
//     * [POST] /users/logIn
//     * @return BaseResponse<PostLoginRes>
//     */
//    @ResponseBody
//    @PostMapping("/logIn")
//    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
//        try{
//            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
//            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 유저정보변경 API
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
//        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(paramType = "path");
//            }
//            //같다면 유저네임 변경
//            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
//            userService.modifyUserName(patchUserReq);
//
//            String result = "";
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
}