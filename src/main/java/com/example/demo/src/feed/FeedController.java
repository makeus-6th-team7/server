package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.feed.FeedProvider;
import com.example.demo.src.feed.FeedService;
import com.example.demo.src.feed.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 게시물 상세조회 API
     * [GET] /feeds/:feedId
     * @return BaseResponse<GetFeedRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{feedId}") // (GET) 127.0.0.1:9000/feeds/:feedId
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
     *  게시물 좋아요 / 좋아요 취소 API
     * [POST] /feeds/{feedId}/like
     * @return BaseResponse<GetCommentRes>
     */
    // Path-variable
    @ResponseBody
    @PostMapping("/{feedId}/like") // (Post) 127.0.0.1:9000/feeds/:feedId/like
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
     * @return BaseResponse<GetCommentRes>
     */
    // Path-variable
    @ResponseBody
    @PostMapping("/{feedId}/report") // (POST) 127.0.0.1:9000/feeds/:feedId/report
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
    @ResponseBody
    @GetMapping("/{feedId}/comments") // (GET) 127.0.0.1:9000/feeds/:feedId/comments
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

}
