package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.feed.model.*;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
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

import java.util.Comparator;
import java.util.List;

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
    public GetHomeFeedRes getHomeFeeds() throws BaseException {

        GetHomeFeedRes getHomeFeedRes = null;

        try {
            getHomeFeedRes = feedDao.getHomeFeeds();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
        return getHomeFeedRes;
    }
    public GetHomeTabFeedRes getHomeTabFeeds(String type) throws BaseException {

        GetHomeTabFeedRes getHomeFeedRes = null;
        if(type.equals("pop")){
            try {
                getHomeFeedRes = feedDao.getPopHomeFeeds();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                throw new BaseException(DATABASE_ERROR);
            }
        }
        else if (type.equals("new")){
            try{
                getHomeFeedRes = feedDao.getNewHomeFeeds();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                throw new BaseException(DATABASE_ERROR);
            }
        }
            else throw new BaseException(HOME_FEED_TYPE_ERROR);
            return getHomeFeedRes;
    }

    public GetFeedRes getFeedDetail(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        GetFeedFromDao getFeedFromDao;
        try {
            getFeedFromDao = feedDao.getFeedDetail(userIdx,feedId);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }

            String address;
            boolean checkAirBnB = false;
        try{
            // 일반 숙소 피드일 경우에 지도API 에서 위치 불러온다. (TO-DO 애초에 업로드 할 때 저장하도록 바꾸기)
            if(getFeedFromDao.getCheckAirBnB().equals("N")){
                KakaoAddressRes kakaoAddressRes = getAdressByCordinates(getFeedFromDao.getLongitude(),getFeedFromDao.getLatitude());
                address = kakaoAddressRes.getAddress();
                checkAirBnB = false;
            }
            // 에어비앤비 일 경우
            else{
                address = getFeedFromDao.getAddress();
            }}catch (Exception exception) {
            address = getFeedFromDao.getAddress();
//            throw new BaseException(KAKAO_LOCATION_SERVER_ERROR);
        }

            // To-do: GetFeedFromDao / GetFeedRes -> 변수들이 겹치므로 부모클래스 만들어서 상속받도록, 복사생성자??
            GetFeedRes getFeedRes = new GetFeedRes(getFeedFromDao.getUserIdx(), getFeedFromDao.getUserId(), getFeedFromDao.getProfileImgUrl(),
                    getFeedFromDao.getTemperature(),checkAirBnB,getFeedFromDao.getRetouchedDegree(),getFeedFromDao.getReview(),
                    getFeedFromDao.getPhotoTool(), getFeedFromDao.getTitle(),address, getFeedFromDao.getAdditionalLocation(),
                    getFeedFromDao.getPrice(),getFeedFromDao.getPeriod(),
                    getFeedFromDao.getLikeNum(),getFeedFromDao.isCheckLike(),getFeedFromDao.getCreatedAt(),
                    getFeedFromDao.getViewNum(),getFeedFromDao.getSavedNum(),getFeedFromDao.getCommentNum(),
                    getFeedFromDao.isCheckReport(),getFeedFromDao.getFeedImgNum(),getFeedFromDao.getPros(),
                    getFeedFromDao.getCons(),getFeedFromDao.getFeedImgUrls(),getFeedFromDao.getTags());
            return getFeedRes;


    }
    public GetKeywordRecomRes getKeywordRecoms(String keyword) throws BaseException {
        try {
            List<String> accomodationKeywords= feedDao.getAccomodationKeywordRecoms(keyword);
            List<String> tagKeywords= feedDao.getTagKeywordRecoms(keyword);
            GetKeywordRecomRes getKeywordRecomRes = new GetKeywordRecomRes(accomodationKeywords,tagKeywords);
            return getKeywordRecomRes;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetSearchResultRes getSearchResults(String keyword) throws BaseException {
        try {
            GetSearchResultRes getSearchResultRes = feedDao.getSearchResults(keyword);
            return getSearchResultRes;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 게시글 숙소의 최저가 링크 반환하는 메소드
     * 네이버 쇼핑 API 사용
     */
    public GetFeedLinksRes getFeedLinks(int feedId) throws BaseException {
        try{
            GetFeedLinksRes getFeedLinksRes;
            // 에어비앤비일 경우 : 사용자가 입력한 예약링크 불러오기
            if(feedDao.checkAirBnB(feedId) == 1){
                getFeedLinksRes = new GetFeedLinksRes();
                String airBnBLink = feedDao.getAirBnBLink(feedId);
                getFeedLinksRes.addItem("에어비앤비", airBnBLink);
            }
            // 에어비앤비 아닐 경우 : 최저가 링크 가져오기 (네이버 API로)
            else{
                // 해당 피드의 title 가져오기
                String title = feedDao.getTitleFromFeedId(feedId);

                RestTemplate rt = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Naver-Client-Id",Secret.NAVER_CLIENT_ID);
                headers.add("X-Naver-Client-Secret",Secret.NAVER_CLIENT_SECRET);
                HttpEntity<MultiValueMap<String,String>> itemRequest = new HttpEntity<>(headers);

                // 네이버 shopping API 요청하기 - GET방식으로 - 그리고 response 변수의 응답 받음.
                ResponseEntity<String> response = rt.exchange(
                        String.format("https://openapi.naver.com/v1/search/shop.json?query=%s",title),
                        HttpMethod.GET,
                        itemRequest,
                        String.class
                );
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                NaverShoppingRes naverShoppingRes = objectMapper.readValue(response.getBody(), NaverShoppingRes.class);
                // 최저가 링크 3개 선택
                getFeedLinksRes = selectLowestPriceItems(naverShoppingRes,3);
            }
            return getFeedLinksRes;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(NAVER_SHOPPING_SERVER_ERROR);
        }
    }
    /**
     * 네이버 쇼핑 API로 부터 받은 정보들-> 최저가링크 num개로 추리는 메소드
     * @param naverShoppingRes 네이버 쇼핑 API로 부터 받은 정보들
     * @param num 최저가 링크 개수
     */
    public GetFeedLinksRes selectLowestPriceItems(NaverShoppingRes naverShoppingRes, int num){
        GetFeedLinksRes getFeedLinksRes = new GetFeedLinksRes();
        // 가격순으로 정렬
        List <NaverShoppingRes.Item> items = naverShoppingRes.getItems();
        items.sort(Comparator.naturalOrder());

        int count = 0;
        for(val item: items ){
            String price = item.getLprice();
            String category = item.getCategory3();
            if( !price.equals("0") && category.equals("국내숙박")){
                getFeedLinksRes.addItem(item.getMallName(),item.getLink());
                count++;
                if(count == num) break;
            }
        }

        return getFeedLinksRes;
    }

    /**
     * (x:경도, y:위도) 를 입력으로 받아 주소를 반환하는 메소드
     * 카카오 로컬 API 사용
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
