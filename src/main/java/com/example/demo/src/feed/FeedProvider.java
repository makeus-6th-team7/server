package com.example.demo.src.feed;
import com.example.demo.config.BaseException;
import com.example.demo.src.feed.model.GetCommentRes;
import com.example.demo.src.feed.model.GetFeedRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public GetFeedRes getFeedDetail(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            GetFeedRes getFeedRes = feedDao.getFeedDetail(userIdx,feedId);
            return getFeedRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetCommentRes> getComments(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            List<GetCommentRes> getCommentRes = feedDao.getComments(userIdx,feedId);
            return getCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
