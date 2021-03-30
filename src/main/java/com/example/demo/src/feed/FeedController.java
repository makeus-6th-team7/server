package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.feed.model.*;
import com.example.demo.utils.JwtService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("feeds")
public class FeedController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FeedProvider feedProvider;
    //@Autowired
    private final FeedService feedService;
    @Autowired
    private final JwtService jwtService;

    public FeedController(FeedProvider feedProvider, FeedService feedService, JwtService jwtService) {
        this.feedProvider = feedProvider;
        this.feedService = feedService;
        this.jwtService = jwtService;

    }
    /**
     * 홈화면 조회 API
     * [POST] /feeds/home
     * @return BaseResponse<GetHomeFeedRes>
     */
    // Path-variable
    @ApiOperation(value = "홈화면 조회 API ")
    @ResponseBody
    @PostMapping("/home") // (Post) http://52.79.187.77/home
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
    })
    public BaseResponse<GetHomeFeedRes> getHomeTabFeeds(){
        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();
            GetHomeFeedRes getHomeFeedRes = feedProvider.getHomeFeeds();
            return new BaseResponse<>(getHomeFeedRes);
        } catch(BaseException exception){
            System.out.println(exception.getMessage());
            return new BaseResponse<>((exception.getStatus()));

        }
    }
    /**
     * 홈화면(인기/최신) 조회 API
     * [POST] /feeds/home/tab
     * @return BaseResponse<GetHomeFeedRes>
     */
    // Path-variable
    @ApiOperation(value = "홈화면(인기/최신) 조회 API ")
    @ResponseBody
    @PostMapping("/home/tab") // (Post) http://52.79.187.77/home/tab
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
    })
    public BaseResponse<GetHomeTabFeedRes> getHomeTabFeeds(@Valid @RequestBody GetHomeTabFeedReq getHomeFeedReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            ResponseEntity<String> error = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
            return new BaseResponse<>(error);
        }
        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();
            GetHomeTabFeedRes getHomeFeedRes = feedProvider.getHomeTabFeeds(getHomeFeedReq.getType());
            return new BaseResponse<>(getHomeFeedRes);
        } catch(BaseException exception){
            System.out.println(exception.getMessage());
            return new BaseResponse<>((exception.getStatus()));

        }

    }

    /**
     * 일반 숙소 게시물 등록 API
     * [POST] /feeds
     * @return BaseResponse<PostFeedRes>
     */
    // Path-variable
    @ApiOperation(value = "일반 숙소 게시물 생성 API")
    @ResponseBody
    @PostMapping("normal") // (POST) http://52.79.187.77/feeds
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
    })
    public BaseResponse<PostFeedRes> postNormalFeeds(@Valid @RequestBody PostNormalFeedReq postNormalFeedReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            ResponseEntity<String> error = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
            return new BaseResponse<>(error);
        }
        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();
            PostFeedRes postFeedRes = feedService.postNormalFeeds(userIdx, postNormalFeedReq);
            return new BaseResponse<>(postFeedRes);
        } catch(BaseException exception){
            System.out.println(exception.getMessage());
            return new BaseResponse<>((exception.getStatus()));

        }

    }
    /**
     * 에어비앤비 숙소 게시물 등록 API
     * [POST] /feeds
     * @return BaseResponse<PostFeedRes>
     */
    // Path-variable
    @ApiOperation(value = "에어비앤비 숙소 게시물 생성 API")
    @ResponseBody
    @PostMapping("airbnb") // (POST) http://52.79.187.77/feeds
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
    })
    public BaseResponse<PostFeedRes> postAirBnBFeeds(@Valid @RequestBody PostAirBnBFeedReq postAirBnBFeedReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            ResponseEntity<String> error = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
            return new BaseResponse<>(error);
        }
        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();
            PostFeedRes postFeedRes = feedService.postAirBnBFeeds(userIdx, postAirBnBFeedReq);
            return new BaseResponse<>(postFeedRes);
        } catch(BaseException exception){
            System.out.println(exception.getMessage());
            return new BaseResponse<>((exception.getStatus()));

        }

    }
    /**
     * 검색어 추천 API
     * [GET] /feeds/search/keyword-recom
     * @return BaseResponse<GetSearchResultRes>
     */
    // Path-variable
    @ApiOperation(value = "검색어 추천 API")
    @ResponseBody
    @PostMapping("/search/keyword-recom") // (Post) http://52.79.187.77/feeds/search/keyword-recom
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
    })
    public BaseResponse<GetKeywordRecomRes> getSearchResults(@Valid @RequestBody GetKeywordRecomReq getKeywordRecomReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            ResponseEntity<String> error = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
            return new BaseResponse<>(error);
        }
        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();
            GetKeywordRecomRes getKeywordRecomRes = feedProvider.getKeywordRecoms(getKeywordRecomReq.getKeyword());
            return new BaseResponse<>(getKeywordRecomRes);
        } catch(BaseException exception){
            System.out.println(exception.getMessage());
            return new BaseResponse<>((exception.getStatus()));

        }

    }
    /**
     * 검색결과 조회 API
     * [GET] /feeds/search
     * @return BaseResponse<GetSearchResultRes>
     */
    // Path-variable
    @ApiOperation(value = "검색결과 조회 API")
    @ResponseBody
    @PostMapping("/search") // (Post) http://52.79.187.77/feeds/search
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
    })
    public BaseResponse<GetSearchResultRes> getSearchResults(@Valid @RequestBody GetSearchResultReq getSearchResultReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            ResponseEntity<String> error = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
            return new BaseResponse<>(error);
        }
        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();
            GetSearchResultRes getSearchResultRes = feedProvider.getSearchResults(getSearchResultReq.getKeyword());
            return new BaseResponse<>(getSearchResultRes);
        } catch(BaseException exception){
            System.out.println(exception.getMessage());
            return new BaseResponse<>((exception.getStatus()));

        }

    }

    /**
     * 게시물 상세조회 API
     * [GET] /feeds/:feedId
     * @return BaseResponse<GetFeedRes>
     */
    // Path-variable
    @ApiOperation(value = "게시물 상세조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @GetMapping("/{feedId}") // (GET) http://52.79.187.77/feeds/:feedId
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2020, message = "존재하지 않는 feedId입니다.",response = BaseResponse.class),
            @ApiResponse(code = 4020, message = "카카오 서버에서 주소정보 요청에 실패했습니다.",response = BaseResponse.class)
    })
    public BaseResponse<GetFeedRes> getFeedDetail(@PathVariable("feedId") int feedId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // Get FeedDetail
            GetFeedRes getFeedRes = feedProvider.getFeedDetail(userIdx,feedId);
            return new BaseResponse<>(getFeedRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 게시물 링크조회 API
     * [GET] /feeds/:feedId/links
     * @return BaseResponse<List<String>>
     */
    // Path-variable
    @ApiOperation(value = "게시물 링크조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @GetMapping("/{feedId}/links") // (GET) http://52.79.187.77/feeds/:feedId/links
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2020, message = "존재하지 않는 feedId입니다.",response = BaseResponse.class),
            @ApiResponse(code = 4021, message = "네이버 서버에서 쇼핑정보 요청에 실패했습니다.",response = BaseResponse.class)

    })
    public BaseResponse<GetFeedLinksRes> getFeedLinks(@PathVariable("feedId") int feedId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // Get FeedLinks
            GetFeedLinksRes getFeedLinksRes = feedProvider.getFeedLinks(feedId);
            return new BaseResponse<>(getFeedLinksRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     *  게시물 좋아요 / 좋아요 취소 API
     * [POST] /feeds/{feedId}/like
     * @return BaseResponse
     */
    // Path-variable
    @ApiOperation(value = "게시물 좋아요 / 좋아요 취소 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @PostMapping("/{feedId}/like") // (Post) http://52.79.187.77/feeds/:feedId/like
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2020, message = "존재하지 않는 feedId입니다.",response = BaseResponse.class)
    })
    public BaseResponse setFeedLike(@PathVariable("feedId") int feedId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // set feed like
            feedService.setFeedLike(userIdx,feedId);
            return new BaseResponse<>();
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     *  게시물 신고 / 신고 취소 API
     * [POST] /feeds/{feedId}/report
     * @return BaseResponse
     */
    // Path-variable
    @ApiOperation(value = "게시물 신고 / 신고 취소 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @PostMapping("/{feedId}/report") // (POST) http://52.79.187.77/feeds/:feedId/report
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2020, message = "존재하지 않는 feedId입니다.",response = BaseResponse.class)
    })
    public BaseResponse setFeedReport(@PathVariable("feedId") int feedId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // set feed like
            feedService.setFeedReport(userIdx,feedId);
            return new BaseResponse<>();
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 댓글 조회 API
     * [GET] /feeds/{feedId}/comments
     * @return BaseResponse<GetCommentRes>
     */
    // Path-variable
    @ApiOperation(value = "댓글 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @GetMapping("/{feedId}/comments") // (GET) http://52.79.187.77/feeds/:feedId/comments
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2020, message = "존재하지 않는 feedId입니다.",response = BaseResponse.class)
    })
    public BaseResponse<GetCommentRes> getComments(@PathVariable("feedId") int feedId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // Get comments
            GetCommentRes getCommentRes = feedProvider.getComments(userIdx,feedId);
            return new BaseResponse<>(getCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 댓글 생성 API
     * [POST] /feeds/{feedId}/comments
     * @return BaseResponse<PostCommentRes>
     */
    // Path-variable
    @ApiOperation(value = "댓글 생성 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "int", paramType = "path")
    })
    @ResponseBody
    @PostMapping("/{feedId}/comments") // (POST) http://52.79.187.77/feeds/:feedId/comments
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2020, message = "존재하지 않는 feedId입니다.",response = BaseResponse.class)
    })
    public BaseResponse<PostCommentRes> postComments(@PathVariable("feedId") int feedId, @RequestBody PostCommentReq postCommentReq) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // Post comments
            PostCommentRes postCommentRes = feedService.postComments(userIdx,feedId,postCommentReq.getContent());
            return new BaseResponse<>(postCommentRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     *  댓글 좋아요 / 좋아요 취소 API
     * [POST] feeds/comments/{commentId}/like
     * @return BaseResponse
     */
    // Path-variable
    @ApiOperation(value = "댓글 좋아요 / 좋아요 취소 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @PostMapping("/comments/{commentId}/like") // (Post) http://52.79.187.77/feeds/comments/:commentId/like
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2021, message = "존재하지 않는 commentId입니다.",response = BaseResponse.class)
    })
    public BaseResponse setCommentLike(@PathVariable("commentId") int commentId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // set comment like
            feedService.setCommentLike(userIdx,commentId);
            return new BaseResponse<>();
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 게시물 삭제 API
     * [DELETE] feeds/{feedId}
     * @return BaseResponse
     */
    // Path-variable
    @ApiOperation(value = "게시물 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "게시물 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @DeleteMapping("/feeds/{feedId}") // (DELETE) http://52.79.187.77/feeds/:feedId
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2020, message = "존재하지 않는 feedId입니다.",response = BaseResponse.class)
    })
    public BaseResponse deleteFeeds(@PathVariable("feedId") int feedId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // delete feeds
            feedService.deleteFeed(userIdx,feedId);
            return new BaseResponse<>();
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 댓글 삭제 API
     * [DELETE] feeds/comments/{commentId}
     * @return BaseResponse
     */
    // Path-variable
    @ApiOperation(value = "댓글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 식별자", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @DeleteMapping("/comments/{commentId}") // (DELETE) http://52.79.187.77/feeds/comments/:commentId
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.",response = BaseResponse.class ),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요.",response = BaseResponse.class),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.",response = BaseResponse.class),
            @ApiResponse(code = 2021, message = "존재하지 않는 commentId입니다.",response = BaseResponse.class)
    })
    public BaseResponse deleteComments(@PathVariable("commentId") int commentId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // delete comments
            feedService.deleteComment(userIdx,commentId);
            return new BaseResponse<>();
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
