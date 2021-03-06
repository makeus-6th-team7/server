package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.feed.FeedProvider;
import com.example.demo.src.feed.FeedService;
import com.example.demo.src.feed.model.GetFeedRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feeds")
public class FeedController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FeedProvider feedProvider;
    //@Autowired
    //private final FeedService feedService;
    @Autowired
    private final JwtService jwtService;

    //public FeedController(FeedProvider feedProvider, FeedService feedService, JwtService jwtService) {
    public FeedController(FeedProvider feedProvider, JwtService jwtService) {
        this.feedProvider = feedProvider;
        //this.feedService = feedService;
        this.jwtService = jwtService;
    }
        /**
     * 게시물 상세조회 API
     * [GET] /feeds/:feedId
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{feedId}") // (GET) 127.0.0.1:9000/feeds/:feedId
    public BaseResponse<GetFeedRes> getUser(@PathVariable("feedId") int feedId) {

        int userIdx = 0;
        try {
            //jwt에서 idx 추출.
            userIdx = jwtService.getUserIdx();

            // Get Users FeedDetail
            GetFeedRes getFeedRes = feedProvider.getFeedDetail(userIdx,feedId);
            return new BaseResponse<>(getFeedRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
