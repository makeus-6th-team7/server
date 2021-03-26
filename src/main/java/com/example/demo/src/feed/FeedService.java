package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.src.feed.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;
@Service
public class FeedService {
    private final FeedDao feedDao;

    @Autowired
    public FeedService(FeedDao feedDao) {
        this.feedDao = feedDao;
    }

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PostFeedRes postNormalFeeds(int userIdx, PostNormalFeedReq postNormalFeedReq) throws BaseException {
        try {
            int feedId = feedDao.postNormalFeeds(userIdx,postNormalFeedReq);
            return new PostFeedRes(feedId);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostFeedRes postAirBnBFeeds(int userIdx, PostAirBnBFeedReq postAirBnBFeedReq) throws BaseException {
        try {
            int feedId = feedDao.postAirBnBFeeds(userIdx,postAirBnBFeedReq);
            return new PostFeedRes(feedId);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void setFeedLike(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            feedDao.setFeedLike(userIdx,feedId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void setCommentLike(int userIdx, int commentId) throws BaseException {
        if(feedDao.checkCommentId(commentId)==0) throw new BaseException(INVALID_COMMENT_ID);
        try {
            feedDao.setCommentLike(userIdx,commentId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void setFeedReport(int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            feedDao.setFeedReport(userIdx,feedId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostCommentRes postComments(int userIdx, int feedId, String content) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        try {
            int commentId = feedDao.postComments(userIdx,feedId,content);
            return new PostCommentRes(commentId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteFeed (int userIdx, int feedId) throws BaseException {
        if(feedDao.checkFeedId(feedId)==0) throw new BaseException(INVALID_FEED_ID);
        // 다른 사람의 게시물을 삭제하려고 할 때
        if(userIdx != feedDao.getUserIdxOfFeed(feedId)) throw new BaseException(INVALID_USER_REQ);
        try {
            feedDao.deleteFeeds(feedId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteComment (int userIdx, int commentId) throws BaseException {
        if(feedDao.checkCommentId(commentId)==0) throw new BaseException(INVALID_COMMENT_ID);
        // 다른 사람의 댓글을 삭제하려고 할 때
        if(userIdx != feedDao.getUserIdxOfComment(commentId)) throw new BaseException(INVALID_USER_REQ);
        try {
            feedDao.deleteComments(commentId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
