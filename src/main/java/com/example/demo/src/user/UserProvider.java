package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public KakaoProfileRes getKakaoProfile(PostLoginReq postLoginReq) throws BaseException{
        try{
            System.out.println("1");
            RestTemplate rt = new RestTemplate();
            //HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","Bearer "+postLoginReq.getAccessToken());
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            //HttpHeader를 오브젝트에 담기
            HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity<>(headers);

            // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoProfileRequest,
                    String.class
            );
            System.out.println("2");
            System.out.println(response.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            KakaoProfileRes kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfileRes.class);
            System.out.println("3");
            return kakaoProfile;
        }
        catch (Exception exception) {
            //exception 다시 생각하기
            throw new BaseException(INVALID_ACCESS_TOKEN);
        }
    }

    // 카카오 id로 이미 회원가입이 되어있는지 체크
    public int checkUserByKakaoId(Integer kakaoId) throws BaseException{
        try{
            return userDao.checkKakaoId(kakaoId);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes logIn(Integer kakaoId) throws BaseException{
        int userIdx = userDao.getUserIdxByKakaoId(kakaoId);
        String jwt = jwtService.createJwt(userIdx);
        return new PostLoginRes(userIdx,jwt,false);

    }
    // userId가 등록됐는지 check
    public boolean checkUserIdIsKakaoId(int userIdx, int kakaoId)throws BaseException{
        try{
            return userDao.getUserIdByIdx(userIdx).equals(Integer.toString(kakaoId));
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    public List<GetUserRes> getUsers() throws BaseException{
//        try{
//            List<GetUserRes> getUserRes = userDao.getUsers();
//            return getUserRes;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public List<GetUserRes> getUsersByEmail(String email) throws BaseException{
//        try{
//            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
//            return getUsersRes;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//
//    public GetUserRes getUser(int userIdx) throws BaseException {
//        try {
//            GetUserRes getUserRes = userDao.getUser(userIdx);
//            return getUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public int checkEmail(String email) throws BaseException{
//        try{
//            return userDao.checkEmail(email);
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//


}
