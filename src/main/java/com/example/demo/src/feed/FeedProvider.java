package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.feed.model.GetCommentRes;
import com.example.demo.src.feed.model.GetFeedFromDao;
import com.example.demo.src.feed.model.GetFeedRes;
import com.example.demo.src.feed.model.KakaoAddressRes;
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

@Service
public class FeedProvider {

    private final FeedDao feedDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FeedProvider(FeedDao feedDao, JwtService jwtService) {
        this.feedDao = feedDao;
        this.jwtService = jwtService;
    }

    public GetFeedRes getFeedDetail(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            GetFeedFromDao getFeedFromDao = feedDao.getFeedDetail(userIdx,feedId);
            KakaoAddressRes kakaoAddressRes = getAdressByCordinates(getFeedFromDao.getLongitude(),getFeedFromDao.getLatitude());
            String address = kakaoAddressRes.getAddress();
            // To-do: GetFeedFromDao / GetFeedRes -> 변수들이 겹치므로 부모클래스 만들어서 상속받도록, 복사생성자??
            GetFeedRes getFeedRes = new GetFeedRes(getFeedFromDao.getUserIdx(), getFeedFromDao.getUserId(), getFeedFromDao.getProfileImgUrl(),
                                                   getFeedFromDao.getTemperature(),getFeedFromDao.getRetouchedDegree(),getFeedFromDao.getReview(),
                                                   getFeedFromDao.getTitle(),address, getFeedFromDao.getPrice(),getFeedFromDao.getPeriod(),
                                                   getFeedFromDao.getLikeNum(),getFeedFromDao.isCheckLike(),getFeedFromDao.getCreatedAt(),
                                                   getFeedFromDao.getViewNum(),getFeedFromDao.getSavedNum(),getFeedFromDao.getCommentNum(),
                                                   getFeedFromDao.isCheckReport(),getFeedFromDao.getFeedImgNum(),getFeedFromDao.getPros(),
                                                   getFeedFromDao.getCons(),getFeedFromDao.getFeedImgUrls(),getFeedFromDao.getTags());
            return getFeedRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * (x:경도, y:위도) 를 입력으로 받아 주소를 반환하는 메소드
     */
    //
    public KakaoAddressRes getAdressByCordinates(Double x, Double y) throws BaseException {
        try{
            RestTemplate rt = new RestTemplate();
            //HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","KakaoAK "+ Secret.KAKAO_REST_API_KEY);
            headers.add("Content-type", "application/json;charset=utf-8");

            //HttpBody 오브젝트 생성
//            MultiValueMap<String, Double> params = new LinkedMultiValueMap<>();
//            params.add("x", x);
//            params.add("y", y);
//            System.out.println(params);

            //HttpBody를 하나의 오브젝트에 담기
            HttpEntity<MultiValueMap<String,String>> locationRequest = new HttpEntity<>(headers);
//            HttpEntity<MultiValueMap<String,Double>> locationRequest = new HttpEntity<>(params,headers);

            // Http 요청하기 - GET방식으로 - 그리고 response 변수의 응답 받음.
            ResponseEntity<String> response = rt.exchange(
                    //"https://dapi.kakao.com/v2/local/geo/coord2address.json",
                    String.format("https://dapi.kakao.com/v2/local/geo/coord2address.json?x=%s&y=%s",x,y),
                    HttpMethod.GET,
                    locationRequest,
                    String.class
            );
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            KakaoAddressRes kakaoAddressRes = objectMapper.readValue(response.getBody(), KakaoAddressRes.class);
            return kakaoAddressRes;
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(KAKAO_LOCATION_SERVER_ERROR);
        }
    }

    public GetCommentRes getComments(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            GetCommentRes getCommentRes = feedDao.getComments(userIdx,feedId);
            return getCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
